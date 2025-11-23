package api.afrilangue.serrvices.assessment;

import api.afrilangue.models.RequestAssessment;
import api.afrilangue.models.ResponseAssessment;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public interface AssessmentFactory {
    ResponseAssessment checkAssessment(RequestAssessment requestAssessment);
}
