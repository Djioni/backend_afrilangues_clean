package api.afrilangue.dto.request;

import api.afrilangue.models.ExerciseMedia;
import api.afrilangue.models.ExerciseType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Getter
@Builder
public class ExerciseRequest {
    private String title;

    private ExerciseType type;

    private Object content;

    private int difficultyLevel;

    private String lessonSection;

    private List<String> answers;

    private List<ExerciseMedia> exerciseMedia;
}
