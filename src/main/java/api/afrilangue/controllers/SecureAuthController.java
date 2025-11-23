package api.afrilangue.controllers;

import api.afrilangue.dto.DecryptedPayload;
import api.afrilangue.dto.EncryptedPayload;
import api.afrilangue.dto.request.LoginRequest;
import api.afrilangue.dto.request.SignupRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.dto.response.JwtResponse;
import api.afrilangue.serrvices.EnvelopeCrypto;
import api.afrilangue.serrvices.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth/secure", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class SecureAuthController {

    private final EnvelopeCrypto envelopeCrypto;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signin(@Valid @RequestBody EncryptedPayload payload) {
        try {
            DecryptedPayload decryptedPayload = envelopeCrypto.decrypt(payload);
            LoginRequest loginRequest = objectMapper.readValue(decryptedPayload.getJson(), LoginRequest.class);

            JwtResponse jwt = userService.getJwtResponse(loginRequest);
            return ResponseEntity.ok(jwt);

        } catch (IllegalArgumentException e) {
              log.warn("[secure/signin] Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            // Tout le reste -> 500
            log.error("[secure/signin] Server error", e);
            return ResponseEntity.status(500).body(Map.of("error", "Erreur serveur"));
        }
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signup(@Valid @RequestBody EncryptedPayload payload) {
        try {
            DecryptedPayload dec = envelopeCrypto.decrypt(payload);
            SignupRequest req = objectMapper.readValue(dec.getJson(), SignupRequest.class);

            userService.createAccount(req);
            return ResponseEntity.ok(
                    DefaultResponse.builder().data("Utilisateur enregistré avec succès !").build()
            );

        } catch (IllegalArgumentException e) {
            log.warn("[secure/signup] Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            log.error("[secure/signup] Server error", e);
            return ResponseEntity.status(500).body(Map.of("error", "Erreur serveur"));
        }
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<?> badJson(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", "JSON invalide"));
    }
}
