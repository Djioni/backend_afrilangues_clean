package api.afrilangue.serrvices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Component
public class RsaKeyProvider {
    private final ResourceLoader loader;
    private final String inlinePubB64;
    private final String inlinePrivB64;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public RsaKeyProvider(
            ResourceLoader loader,
            @Value("${app.rsa.public.pem.b64:}") String inlinePubB64,
            @Value("${app.rsa.private.pem.b64:}") String inlinePrivB64
    ) {
        this.loader = loader;
        this.inlinePubB64 = inlinePubB64;
        this.inlinePrivB64 = inlinePrivB64;
    }

    private static byte[] pemToDer(String pem) {
        String cleaned = pem.replaceAll("-----BEGIN (.*)-----", "")
                .replaceAll("-----END (.*)-----", "")
                .replaceAll("\\s", "");
        return Base64.getDecoder().decode(cleaned);
    }

    private String readPemFlexible(String classpath, String filePath, String inlineB64, String kind) {
        try {
            if (inlineB64 != null && !inlineB64.isBlank()) {
                return new String(Base64.getDecoder().decode(inlineB64.trim()), StandardCharsets.UTF_8);
            }

            Resource cp = loader.getResource("classpath:" + classpath);
            if (cp.exists()) {
                return new String(StreamUtils.copyToByteArray(cp.getInputStream()), StandardCharsets.UTF_8);
            }

            Resource fs = loader.getResource("file:" + filePath);
            if (fs.exists()) {
                return new String(StreamUtils.copyToByteArray(fs.getInputStream()), StandardCharsets.UTF_8);
            }
        } catch (Exception ignored) {}
        throw new RuntimeException(kind + " introuvable (inline/classpath/file): " + classpath + " | " + filePath);
    }

    public synchronized PublicKey getPublicKey() {
        if (publicKey == null) {
            try {
                String pem = readPemFlexible("keys/public.key", "keys/public.key", inlinePubB64, "Clé publique");
                byte[] der = pemToDer(pem);
                publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(der));
            } catch (Exception e) {
                throw new RuntimeException("Chargement clé publique RSA", e);
            }
        }
        return publicKey;
    }

    public synchronized PrivateKey getPrivateKey() {
        if (privateKey == null) {
            try {
                String pem = readPemFlexible("keys/private.key", "keys/private.key", inlinePrivB64, "Clé privée");
                byte[] der = pemToDer(pem);
                privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(der));
            } catch (Exception e) {
                throw new RuntimeException("Chargement clé privée RSA", e);
            }
        }
        return privateKey;
    }

    public String exportPublicKeyPemAsBase64() {
        try {
            var pub = getPublicKey();
            String pem = "-----BEGIN PUBLIC KEY-----\n"
                    + Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(pub.getEncoded())
                    + "\n-----END PUBLIC KEY-----\n";
            return Base64.getEncoder().encodeToString(pem.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Export PEM public", e);
        }
    }
}
