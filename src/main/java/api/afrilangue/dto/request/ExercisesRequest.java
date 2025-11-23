package api.afrilangue.dto.request;

import api.afrilangue.models.ExerciseAndAnswers;
import api.afrilangue.models.ExerciseMedia;
import api.afrilangue.models.ExerciseType;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
public class ExercisesRequest {
    private String title;
    private ExerciseType types;
    private Set<ExerciseAndAnswers> exerciseAndAnswers;
    private String lessonSection;
    private List<ExerciseMedia> exerciseMedia;
    private List<String> exerciseWords;
}
