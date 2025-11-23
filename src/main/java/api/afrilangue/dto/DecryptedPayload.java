package api.afrilangue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */

@AllArgsConstructor
@Getter
public class DecryptedPayload {
    private final String json;
}
