package api.afrilangue.serrvices;

import api.afrilangue.dto.request.LoginRequest;
import api.afrilangue.dto.request.SignupRequest;
import api.afrilangue.dto.response.JwtResponse;
import api.afrilangue.helpers.JwtUtils;
import api.afrilangue.models.ERole;
import api.afrilangue.models.Language;
import api.afrilangue.models.Role;
import api.afrilangue.models.User;
import api.afrilangue.repositories.RoleRepository;
import api.afrilangue.repositories.UserRepository;
import api.afrilangue.serrvices.language.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;


    private final JwtUtils jwtUtils;

    private final LanguageService languageService;


    public JwtResponse getJwtResponse(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return buildJwtResponse(jwt, userDetails, roles);
    }

    public void createAccount(SignupRequest signUpRequest) {
        Set<Role> roles = getRoles(signUpRequest);
        User user = buildUser(signUpRequest, roles);
        userRepository.save(user);
    }

    private static JwtResponse buildJwtResponse(String jwt, UserDetailsImpl userDetails, List<String> roles) {
        return JwtResponse.builder()
                .token(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }

    private Set<Role> getRoles(SignupRequest signUpRequest) {
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Erreur: le rôle n'existe pas"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Erreur: le rôle n'existe pas"));
                        roles.add(adminRole);

                        break;
                    case "teacher":
                        Role modRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                                .orElseThrow(() -> new RuntimeException("Erreur: le rôle n'existe pas"));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }

    private Set<Language> getLanguage(SignupRequest signUpRequest) {
        Set<Language> languages = new HashSet<>();
        signUpRequest.getLanguage().forEach(language -> {
                    Language userLanguage = languageService.findByName(language);
                    languages.add(userLanguage);
                }

        );
        return languages;
    }

    private User buildUser(SignupRequest signUpRequest, Set<Role> roles) {
        return User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .roles(roles)
                .language(getLanguage(signUpRequest))
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .address(signUpRequest.getAddress())
                .phone(signUpRequest.getPhone())
                .build();
    }
}
