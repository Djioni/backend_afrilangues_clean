package api.afrilangue.serrvices;

import api.afrilangue.dto.DecryptedPayload;
import api.afrilangue.dto.EncryptedPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.MGF1ParameterSpec;
import java.util.Base64;
import java.util.Map;

@Component
public class EnvelopeCrypto {

    private final RsaKeyProvider rsa;
    private final String oaepHash;
    private final boolean tryFallbackSha1;

    public EnvelopeCrypto(
            RsaKeyProvider rsa,
            @Value("${app.crypto.rsa.oaep:SHA-256}") String oaepHash,
            @Value("${app.crypto.rsa.oaep.fallback-sha1:false}") boolean tryFallbackSha1
    ) {
        this.rsa = rsa;
        this.oaepHash = oaepHash == null ? "SHA-256" : oaepHash.toUpperCase();
        this.tryFallbackSha1 = tryFallbackSha1;
    }

    public String publicKeyPemBase64() {
        return rsa.exportPublicKeyPemAsBase64();
    }


    public DecryptedPayload decrypt(EncryptedPayload payload) {
        if (payload == null) throw new IllegalArgumentException("Payload absent");
        Map<String, String> map = Map.of(
                "ekey", nvl(payload.getEkey()),
                "iv", nvl(payload.getIv()),
                "ciphertext", nvl(payload.getCiphertext())
        );
        byte[] json = decrypt(map);
        return new DecryptedPayload(new String(json, StandardCharsets.UTF_8));
    }


    public byte[] decrypt(Map<String, String> body) {
        if (body == null) throw new IllegalArgumentException("Payload absent");

        String ekeyB64 = safe(body.get("ekey"));
        String ivB64 = safe(body.get("iv"));
        String ctB64 = safe(body.get("ciphertext"));

        if (ekeyB64.isEmpty() || ivB64.isEmpty() || ctB64.isEmpty()) {
            throw new IllegalArgumentException("Payload incomplet: ekey/iv/ciphertext requis");
        }

        try {
            Base64.Decoder B64 = Base64.getDecoder();

            byte[] ekey = B64.decode(ekeyB64);
            byte[] iv = B64.decode(ivB64);
            byte[] ct = B64.decode(ctB64);


            PrivateKey priv = rsa.getPrivateKey();
            if (!(priv instanceof RSAPrivateKey)) {
                throw new IllegalStateException("Clé privée RSA attendue");
            }
            int modulusBytes = (((RSAPrivateKey) priv).getModulus().bitLength() + 7) / 8;
            if (ekey.length != modulusBytes) {
                throw new IllegalArgumentException("ekey invalide: " + ekey.length + " octets (attendu " + modulusBytes + ")");
            }
            if (iv.length != 12) {
                throw new IllegalArgumentException("IV invalide: " + iv.length + " octets (attendu 12)");
            }
            if (ct.length < 17) {
                throw new IllegalArgumentException("Ciphertext trop court: " + ct.length + " (doit contenir données + tag 16o)");
            }

            byte[] aesRaw = rsaOaepDecrypt(ekey, oaepHash);
            if (aesRaw == null && tryFallbackSha1 && !"SHA-1".equals(oaepHash)) {
                aesRaw = rsaOaepDecrypt(ekey, "SHA-1");
                if (aesRaw != null) {
                    throw new IllegalArgumentException("RSA-OAEP déchiffré en SHA-1 (fallback). Aligne client & serveur en SHA-256.");
                }
            }
            if (aesRaw == null) {
                throw new IllegalArgumentException("RSA-OAEP déchiffrement impossible (clé publique ≠ clé privée, ou mauvais hash OAEP)");
            }
            if (aesRaw.length != 32) {
                throw new IllegalArgumentException("Clé AES dérivée invalide: " + aesRaw.length + " octets (attendu 32)");
            }

            Cipher gcm = Cipher.getInstance("AES/GCM/NoPadding");
            gcm.init(Cipher.DECRYPT_MODE, new SecretKeySpec(aesRaw, "AES"), new GCMParameterSpec(128, iv));
            return gcm.doFinal(ct);

        } catch (IllegalArgumentException exception) {
            throw exception;
        } catch (AEADBadTagException e) {
            throw new IllegalArgumentException("Tag GCM invalide (ciphertext altéré ou mauvaise clé AES)", e);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }

    private byte[] rsaOaepDecrypt(byte[] ekey, String hash) {
        try {
            final String upper = hash == null ? "SHA-256" : hash.toUpperCase();
            final String xform = "SHA-1".equals(upper)
                    ? "RSA/ECB/OAEPWithSHA-1AndMGF1Padding"
                    : "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

            Cipher rsaCipher = Cipher.getInstance(xform);
            OAEPParameterSpec spec = new OAEPParameterSpec(
                    upper,
                    "MGF1",
                    "SHA-1".equals(upper) ? MGF1ParameterSpec.SHA1 : MGF1ParameterSpec.SHA256,
                    PSource.PSpecified.DEFAULT
            );
            rsaCipher.init(Cipher.DECRYPT_MODE, rsa.getPrivateKey(), spec);
            return rsaCipher.doFinal(ekey);
        } catch (BadPaddingException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private static String safe(String s) {
        return s == null ? "" : s.trim();
    }

    private static String nvl(String s) {
        return s == null ? "" : s;
    }
}
