package api.afrilangue.dto;

import lombok.Data;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
public class EncryptedPayload {
    private String ekey;
    private String iv;
    private String ciphertext;
}
