package api.afrilangue.dto.request;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Getter
@Builder
public class VoucherRequest {
    private String code;
}
