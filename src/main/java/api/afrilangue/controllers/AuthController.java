package api.afrilangue.controllers;

import api.afrilangue.dto.request.LoginRequest;
import api.afrilangue.dto.request.SignupRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.serrvices.UserService;
import api.afrilangue.serrvices.mail.MailSenderService;
import api.afrilangue.validators.UserValidators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserValidators userValidators;
    private final MailSenderService mailSenderService;



    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.getJwtResponse(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException {
        DefaultResponse defaultResponse = userValidators.checkSignupRequest(signUpRequest);
        if (defaultResponse != null) {
            return ResponseEntity.badRequest().body(defaultResponse);
        }
        Map<String, Object> props = Map.of("name", signUpRequest.getFirstName());
        var mail = api.afrilangue.models.Mail.builder()
                .mailTo(signUpRequest.getEmail())
                .from("noreply@afrilangues.fr")
                .subject("Confirmation création de compte")
                .props(props)
                .build();
        userService.createAccount(signUpRequest);
        mailSenderService.sendEmail(mail);
        return ResponseEntity.ok(DefaultResponse.builder()
                .data("Utilisateur enregistré avec succes!!!").build());
    }
}
