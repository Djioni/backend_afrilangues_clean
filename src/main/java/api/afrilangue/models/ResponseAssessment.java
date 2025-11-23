package api.afrilangue.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
@Builder
public class ResponseAssessment {
    private final String sentence;
    private final EStatus status;
    private final List<Answer> answerValidation;
    private Integer isNumberPoint;
}
