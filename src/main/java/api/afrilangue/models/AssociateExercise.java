package api.afrilangue.models;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */

@Data
@Builder
public class AssociateExercise {
    private Map<String, String> words;
}
