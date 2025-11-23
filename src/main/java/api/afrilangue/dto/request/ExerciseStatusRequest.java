package api.afrilangue.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseStatusRequest {
    private String exerciseId;
    private String userId;
}
