package api.afrilangue.controllers;

import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.serrvices.EnvelopeCrypto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */

@RestController
@RequestMapping("/api/public-key")
@RequiredArgsConstructor
public class PublicKeyController {
    private final EnvelopeCrypto envelopeCrypto;

    @GetMapping
    public ResponseEntity<DefaultResponse> getPub() {
        return ResponseEntity.ok(
                DefaultResponse.builder().data(envelopeCrypto.publicKeyPemBase64()).build()
        );
    }
}
