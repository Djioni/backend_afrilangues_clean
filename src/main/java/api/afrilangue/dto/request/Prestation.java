package api.afrilangue.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Prestation {
    private String typeInterpretation;
    private String datePrestation;
    private String startTime;
    private String endTime;
    private String language;
}
