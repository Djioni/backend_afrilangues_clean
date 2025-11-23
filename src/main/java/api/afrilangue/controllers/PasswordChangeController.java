package api.afrilangue.controllers;

import api.afrilangue.models.Credentiel;
import api.afrilangue.models.CredentielChangeForgot;
import api.afrilangue.models.CredentielChangeReset;
import api.afrilangue.serrvices.PasswordChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * @author Ibrahima Diallo <ibrahima.diallo2@supinfo.com>
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/password")
@RequiredArgsConstructor
public class PasswordChangeController {

    private final PasswordChangeService passwordChangeService;


    //Send forgot e-mail password
    @PostMapping("/forgot/mail")
    private ResponseEntity sendMail(@RequestBody Credentiel credentiel) throws UnsupportedEncodingException, MessagingException {

        return passwordChangeService.sendMail(credentiel);
    }

    // Check validity of confirmation token
    @GetMapping("/forgot/verify/{token}")
    private ResponseEntity isConfirmationTokenValida(@PathVariable(name = "token") String token) {
        return passwordChangeService.isConfirmationTokenValida(token);
    }

    // Change password from forgot method
    @PostMapping("/forgot/change")
    private ResponseEntity forgotPasswordChange(@RequestBody CredentielChangeForgot credentielChangeForgot) {
        return passwordChangeService.forgotPasswordChange(credentielChangeForgot);
    }

    // ########################### RESET PWD BELLOW ############################
    @PostMapping("/reset")
    private ResponseEntity resetPassword(@RequestBody CredentielChangeReset credentielChangeReset) {
        return passwordChangeService.resetPassword(credentielChangeReset);
    }


}
