package api.afrilangue.serrvices.user;

import api.afrilangue.dto.request.UpdateUserRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.dto.response.UserResponse;
import api.afrilangue.models.ERole;
import api.afrilangue.models.EStatus;
import api.afrilangue.models.Role;
import api.afrilangue.models.User;
import api.afrilangue.repositories.RoleRepository;
import api.afrilangue.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static api.afrilangue.helpers.UserHelper.recoveryUser;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;

    @Override
    public User find(final String id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("L'utilisateur avec l'id " + id + " n'existe pas "));
    }

    @Override
    public List<UserResponse> findAll() {
        UserResponse user = getCurrentUser();
        ERole role = user.getRoles().stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("L'utilisateur n'a pas de langue enregistrée")).getName();

        return role.equals(ERole.ROLE_ADMIN) ? repository.findAll().stream()
                .map(UserInfoServiceImpl::buildBuild).toList() : List.of(user);

    }

    @Override
    public DefaultResponse update(final UpdateUserRequest object) {
        User saved = find(object.getId());
        if (object.getEmail() != null && !object.getEmail().isEmpty()) saved.setEmail(object.getEmail());
        if (object.getRoles() != null && !object.getRoles().isEmpty()) saved.setRoles(getRoles(object.getRoles()));
        if (object.getUsername() != null && !object.getUsername().isEmpty()) saved.setUsername(object.getUsername());
        if (object.getAddress() != null) saved.setAddress(object.getAddress());
        if (object.getFirstName() != null && !object.getFirstName().isEmpty())
            saved.setFirstName(object.getFirstName());
        if (object.getLastName() != null && !object.getLastName().isEmpty()) saved.setLastName(object.getLastName());
        if (object.getPhone() != null && !object.getPhone().isEmpty()) saved.setPhone(object.getPhone());
        if (object.getLanguage() != null && !object.getLanguage().isEmpty()) saved.setLanguage(object.getLanguage());
        saved.setModifiedByUser(recoveryUser());
        repository.save(saved);
        return DefaultResponse.builder()
                .data("A été effectuée avec succès")
                .build();
    }

    @Override
    public DefaultResponse delete(String id) {
        User object = find(id);
        repository.delete(object);
        return DefaultResponse.builder()
                .data("A été effectuée avec succès")
                .build();
    }

    @Override
    public User update(String userId, EStatus status) {
        User user = find(userId);
        if (EStatus.RIGHT.equals(status)) {
            user.setIsNumberPoint(user.getIsNumberPoint() + 1);
        }
        if (EStatus.WRONG.equals(status)) {
            int currentValue = user.getIsNumberPoint() - 1;
            user.setIsNumberPoint(Math.max(currentValue, 0));
        }
        return repository.save(user);
    }

    private Set<Role> getRoles(Set<String> strRoles) {
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

    public UserResponse getCurrentUser() {
        String username = recoveryUser();
        if (username != null) {
            User user = repository.findByUsernameOrEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + username));
            return UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .address(user.getAddress())
                    .language(user.getLanguage())
                    .roles(user.getRoles())
                    .phone(user.getPhone())
                    .lastName(user.getLastName())
                    .firstName(user.getFirstName())
                    .isNumberPoint(user.getIsNumberPoint())
                    .build();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilisateur non autorisé");
    }

    @Override
    public List<UserResponse> getCurrentNumberPointByUser() {
        UserResponse user = getCurrentUser();
        ERole role = user.getRoles().stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("L'utilisateur n'a pas de langue enregistrée")).getName();

        return role.equals(ERole.ROLE_ADMIN) ? repository.findAll().stream()
                .map(UserInfoServiceImpl::buildBuild)
                .filter(userResponse -> userResponse.getIsNumberPoint() !=null)
                .sorted(Comparator.comparing(UserResponse::getIsNumberPoint).reversed())
                .toList() : List.of(user);
    }

    private static UserResponse buildBuild(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .address(user.getAddress())
                .language(user.getLanguage())
                .roles(user.getRoles())
                .phone(user.getPhone())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .build();
    }

}

