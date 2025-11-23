package api.afrilangue.serrvices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */

@Component
public class FieldEncryptor {
    private static final String PREFIX = "gcm:";
    private final byte[] key;

    public FieldEncryptor(
            @Value("${app.crypto.data.key.path:key/data_key.b64}") String keyPath,
            @Value("${app.crypto.data.key.b64:}") String inlineKeyB64,
            ResourceLoader loader
    ) {
        byte[] loaded = null;
        if (inlineKeyB64 != null && !inlineKeyB64.isBlank()) {
            loaded = Base64.getDecoder().decode(inlineKeyB64.trim());
        }
        if (loaded == null) {
            try {
                Resource cp = loader.getResource("classpath:" + keyPath);
                if (cp.exists()) {
                    loaded = Base64.getDecoder().decode(new String(StreamUtils.copyToByteArray(cp.getInputStream()), StandardCharsets.UTF_8).trim());
                }
            } catch (Exception ignored) {}
        }
        if (loaded == null) {
            try {
                Resource fs = loader.getResource("file:" + keyPath);
                if (fs.exists()) {
                    loaded = Base64.getDecoder().decode(new String(StreamUtils.copyToByteArray(fs.getInputStream()), StandardCharsets.UTF_8).trim());
                }
            } catch (Exception ignored) {}
        }
        if (loaded == null) {
            throw new RuntimeException("Chargement clé AES: " + keyPath + " (non trouvé via inline/classpath/file)");
        }
        if (loaded.length != 32) {
            throw new IllegalStateException("Clé AES invalide: " + loaded.length + " octets (attendu 32 octets / 256-bit)");
        }
        this.key = loaded;
    }

    public String encrypt(String plaintext) {
        try {
            if (plaintext == null || plaintext.isBlank()) return plaintext;
            if (plaintext.startsWith(PREFIX)) return plaintext;

            byte[] iv = new byte[12];
            new SecureRandom().nextBytes(iv);

            Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, iv));
            byte[] enc = c.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

            byte[] out = new byte[iv.length + enc.length];
            System.arraycopy(iv, 0, out, 0, iv.length);
            System.arraycopy(enc, 0, out, iv.length, enc.length);

            return PREFIX + Base64.getEncoder().encodeToString(out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

   public String decrypt(String value) {
       if (value == null || value.isBlank()) return value;

       if (!value.startsWith(PREFIX)) return value;

       final int IV_LEN = 12;
       final int TAG_LEN = 16;

       try {

           String b64 = value.substring(PREFIX.length());
           b64 = b64.trim().replaceAll("\\s+", "");
           byte[] all = Base64.getDecoder().decode(b64);

           if (all.length < IV_LEN + TAG_LEN) {
               return value;
           }

           byte[] iv  = java.util.Arrays.copyOfRange(all, 0, IV_LEN);
           byte[] enc = java.util.Arrays.copyOfRange(all, IV_LEN, all.length);

           Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
           c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, iv));
           byte[] plain = c.doFinal(enc);

           return new String(plain, StandardCharsets.UTF_8);

       } catch (IllegalArgumentException e) {
           return value;
       } catch (javax.crypto.AEADBadTagException e) {
           return value;
       } catch (Exception e) {
           return value;
       }
   }

}
