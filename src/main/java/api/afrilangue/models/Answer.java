package api.afrilangue.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    private String content;

    private boolean isCorrect;

    private List<ExerciseMedia> mediaResponse;

    private List<String> words;
}
