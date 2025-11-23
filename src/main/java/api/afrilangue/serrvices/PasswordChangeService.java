package api.afrilangue.serrvices;


import api.afrilangue.models.*;
import api.afrilangue.repositories.IpasswordConfirmationToken;
import api.afrilangue.repositories.UserRepository;
import api.afrilangue.serrvices.mail.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static api.afrilangue.helper.ResponseUtils.error;
import static api.afrilangue.helper.StringGenerator.generateAlphaNumeric;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Ibrahima Diallo <ibrahima.diallo2@supinfo.com>
 */
@Service
@RequiredArgsConstructor
public class PasswordChangeService {


    private final IpasswordConfirmationToken ipasswordConfirmationToken;


    private final UserRepository userRepository;

    private final MailSenderService mailService;

    private final PasswordEncoder encoder;

    //Send forgot e-mail password
    public ResponseEntity sendMail(@RequestBody Credentiel credentiel) throws UnsupportedEncodingException, MessagingException {

        if (credentiel == null) {
            return error("impossible de récupérer des données avec des champs vides");
        }

        User user = getByEmail(credentiel.getMail());

        if (user == null) {
            return error("Impossible de changer le mot de passe d'un utilisateur qui n'existe pas");
        }

        PasswordConfirmationToken passwordConfirmationToken = new PasswordConfirmationToken();
        String token = generateAlphaNumeric(51).toLowerCase();
        passwordConfirmationToken.setToken(token);
        passwordConfirmationToken.setUser(user);
        ipasswordConfirmationToken.save(passwordConfirmationToken);
        Map<String, Object> props = Map.of("name", user.getFirstName(),
                "token", token);
        Mail mail = Mail.builder()
                .mailTo(credentiel.getMail())
                .from("noreply@afrilangues.fr")
                .subject("Récupération de mot de passe")
                .props(props)
                .build();
        mailService.sendEmailPasswordForgot(mail);
        ipasswordConfirmationToken.save(passwordConfirmationToken);
        return ok("Un lien a été envoyé à l'adresse e-mail renseigné.");
    }

    // Check if confirmation token is valid
    private Boolean isConfirmationTokenValid(String token) {
        PasswordConfirmationToken passwordConfirmationToken = getPwdConfirmationToken(token);
        Instant now = Instant.now();
        long minutes = ChronoUnit.MINUTES.between(passwordConfirmationToken.getCreatedDate(), now);

        if (passwordConfirmationToken == null || !passwordConfirmationToken.isActive() || minutes > 60) {
            return false;
        }
        return true;
    }

    // Check validity of confirmation token
    public ResponseEntity isConfirmationTokenValida(@PathVariable(name = "token") String token) {
        if (token == null) {
            error("Le token ne peut pas être nul");
        }
        if (isConfirmationTokenValid(token)) {
            return ok("Token de confirmation valide");
        }

        return error("L'URL de votre mot de passe oublié n'est pas/plus valide (veuillez réessayer).");

    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    public PasswordConfirmationToken getPwdConfirmationToken(String token) {
        return ipasswordConfirmationToken.findByToken(token)
                .orElse(null);
    }

    // Change password from forgot method
    public ResponseEntity forgotPasswordChange(CredentielChangeForgot credentielChangeForgot) {
        if (credentielChangeForgot == null) {
            return error("impossible de récupérer des données avec des champs vides");
        }
        if (!isConfirmationTokenValid(credentielChangeForgot.getToken())) {
            return error("L'URL de votre mot de passe oublié n'est pas/plus valide (veuillez réessayer).");
        }
        if (credentielChangeForgot.getPassword() == null || credentielChangeForgot.getConfirmation() == null || credentielChangeForgot.getPassword() == "" || credentielChangeForgot.getConfirmation() == "") {
            return error("Le mot de passe ne peut pas être vide");
        }
        if (!credentielChangeForgot.getPassword().equals(credentielChangeForgot.getConfirmation())) {
            return error("Les 2 mots de passe ne sont pas identiques.");
        }

        PasswordConfirmationToken passwordConfirmationToken = getPwdConfirmationToken(credentielChangeForgot.getToken());
        User userEntity = getByEmail(passwordConfirmationToken.getUser().getEmail());
        try {
            userEntity.setPassword(encoder.encode(credentielChangeForgot.getPassword()));
            userRepository.save(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return error("Le format du mot de passe n'est pas respecté.");
        }

        passwordConfirmationToken.setActive(false);
        ipasswordConfirmationToken.save(passwordConfirmationToken);
        return ok("Votre mot de passe a été changé avec succès.");

    }

    // ########################### RESET PWD BELLOW ############################
    public ResponseEntity resetPassword(CredentielChangeReset credentielChangeReset) {
        if (credentielChangeReset == null) {
            return error("impossible de récupérer des données avec des champs vides");
        }
        if (credentielChangeReset.getMail() == null || credentielChangeReset.getPassword() == null || credentielChangeReset.getConfirmation() == null || credentielChangeReset.getPassword() == "" || credentielChangeReset.getConfirmation() == "") {
            return error("Le mot de passe ne peut pas être null");
        }
        User userEntity = getByEmail(credentielChangeReset.getMail());
        if (userEntity == null) {
            return notFound().build();
        }
        if (!credentielChangeReset.getPassword().equals(credentielChangeReset.getConfirmation())) {
            return error("Les 2 mots de passe ne sont pas identiques.");
        }

        try {
            userEntity.setPassword(encoder.encode(credentielChangeReset.getPassword()));
            userRepository.save(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return error("TLe format du mot de passe n'est pas respecté.");
        }

        return ok("Votre mot de passe a été changé avec succès.");
    }
}
