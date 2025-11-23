package api.afrilangue.models;

import lombok.*;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestAssessment {
    private String userId;
    private String exerciseId;
    private ExerciseType type;
    private String sentence;
    private Integer order;
    private List<AnswerLetter> answerLetter;

}
