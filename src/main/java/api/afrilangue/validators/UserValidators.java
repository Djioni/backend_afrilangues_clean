package api.afrilangue.validators;

import api.afrilangue.dto.request.SignupRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.repositories.LanguageRepository;
import api.afrilangue.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class UserValidators {
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;

    public DefaultResponse checkSignupRequest(SignupRequest signUpRequest) {
        if (signUpRequest.getUsername() == null || signUpRequest.getUsername().isEmpty()) {
            return DefaultResponse.builder()
                    .data("Erreur: le nom d'utilisateur ne doit pas être vide ou nul!!!")
                    .build();
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return DefaultResponse.builder()
                    .data("Erreur: le nom d'utilisateur est déjà utilisé!!!")
                    .build();
        }
        if (signUpRequest.getFirstName() == null || signUpRequest.getFirstName().isEmpty()) {
            return DefaultResponse.builder()
                    .data("Erreur: le prénom d'utilisateur ne doit pas être vide ou nul!!!")
                    .build();
        }

        if (signUpRequest.getLastName() == null || signUpRequest.getLastName().isEmpty()) {
            return DefaultResponse.builder()
                    .data("Erreur: le nom d'utilisateur ne doit pas être vide ou nul!!!")
                    .build();
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return DefaultResponse.builder()
                    .data("Erreur: l'e-mail est déjà utilisé!")
                    .build();
        }

        if (signUpRequest.getLanguage() == null || signUpRequest.getLanguage().isEmpty()) {
            return DefaultResponse.builder()
                    .data("Erreur: la langue doit être renseignée et valide!")
                    .build();
        }

        if (signUpRequest.getLanguage() == null || signUpRequest.getLanguage().isEmpty()) {
            return DefaultResponse.builder()
                    .data("Erreur: la langue ne doit pas être vide ou nul!!!")
                    .build();
        }
        if (signUpRequest.getRoles() == null || signUpRequest.getRoles().isEmpty()) {
            return DefaultResponse.builder()
                    .data("Erreur: le rôle de l'utilisateur ne doit pas être vide ou nul!!!")
                    .build();
        }
        for (String language : signUpRequest.getLanguage()) {

            if (languageRepository.findByNameIgnoreCase(language)== null ||
                    languageRepository.findByNameIgnoreCase(language).isEmpty()) {
                return DefaultResponse.builder()
                        .data("Erreur: la langue doit être renseignée et valide!")
                        .build();
            }
        }

        return null;
    }
}
