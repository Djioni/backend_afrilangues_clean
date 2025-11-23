package api.afrilangue.dto.request;

import lombok.Data;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
public class ThemeRequest {
    private String name;
    private String language;
    private String image;
}
