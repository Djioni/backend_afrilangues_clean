package api.afrilangue.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Getter
@Builder
public class LanguageResponse {
    private String id;
    private String name;
    private String image;
}
