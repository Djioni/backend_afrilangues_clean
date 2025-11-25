package api.afrilangue.controllers;

import api.afrilangue.dto.request.CaptchaDTO;
import api.afrilangue.serrvices.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@RestController
@RequestMapping("/api/captcha")
@RequiredArgsConstructor
public class CaptchaController {

    public final CaptchaService captchaService;

    @GetMapping("/site-key")
    public ResponseEntity<Map<String, String>> getSiteKey() {
        String siteKey = captchaService.getSiteKey();
        return ResponseEntity.ok(Map.of("siteKey", siteKey));
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitForm(@RequestBody CaptchaDTO captchaDTO) {
        boolean captchaVerified = captchaService.verifyCaptcha(captchaDTO.getRecaptchaToken());
        if (!captchaVerified) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Échec de validation du reCAPTCHA"));
        }
        return ResponseEntity.ok(Map.of("success", true, "message", "Formulaire soumis avec succès !"));
    }
}
