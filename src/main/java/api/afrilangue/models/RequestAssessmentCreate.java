package api.afrilangue.models;

import lombok.Data;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
public class RequestAssessmentCreate {
    private String user;
    private Integer nbPoint;
}
