package api.afrilangue.serrvices.assessment;

import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.Assessment;
import api.afrilangue.models.RequestAssessmentCreate;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public interface AssessmentService {
    DefaultResponse create(RequestAssessmentCreate assessment);

    Assessment find(String id);

}
