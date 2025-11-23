package api.afrilangue.controllers;

import api.afrilangue.models.RequestAssessment;
import api.afrilangue.models.ResponseAssessment;
import api.afrilangue.serrvices.assessment.AssessmentServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/assessment")
@RequiredArgsConstructor
public class AssessmentController {
    private final AssessmentServiceFactory serviceFactory;

    @PostMapping("/")
    public ResponseAssessment performAssessment(@RequestBody RequestAssessment requestAssessment) {
        return serviceFactory.performAssessment(requestAssessment);
    }
}
