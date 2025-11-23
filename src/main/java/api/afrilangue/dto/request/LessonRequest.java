package api.afrilangue.dto.request;

import lombok.Data;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
public class LessonRequest {
    private String name;
    private String theme;
    private String image;
}
