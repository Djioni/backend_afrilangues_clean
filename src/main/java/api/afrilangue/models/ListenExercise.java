package api.afrilangue.models;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */

@Data
@Builder
public class ListenExercise {
    private String phrasing;
    private String translation;
    private String image;
}
