package api.afrilangue.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Getter
@Builder
public class DefaultResponseMedia {
    private final String data;
    private final String type;
}
