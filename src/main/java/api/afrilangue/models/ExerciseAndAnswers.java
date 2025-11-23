package api.afrilangue.models;

import lombok.Data;

import javax.persistence.Column;
import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
public class ExerciseAndAnswers {
    private Integer order;
    private String speaker;
    @Column(columnDefinition="TEXT")
    private String sentence;
    private List<ExerciseMedia> mediaQuestion;
    private List<Answer> answers;
    private List<String> questionWords;
}
