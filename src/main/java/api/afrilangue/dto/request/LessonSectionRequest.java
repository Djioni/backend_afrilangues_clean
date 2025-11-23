package api.afrilangue.dto.request;

import lombok.Data;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
public class LessonSectionRequest {
    private String title;

    private String content;

    private String lesson;

    private String image;
}
