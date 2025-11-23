package api.afrilangue.serrvices.assessment;

import api.afrilangue.models.*;
import api.afrilangue.serrvices.exercice.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static api.afrilangue.serrvices.assessment.AssessmentUtils.*;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class TranslationAssessment implements AssessmentFactory {
    private final ExerciseService service;


    @Override
    public ResponseAssessment checkAssessment(RequestAssessment requestAssessment) {
        Exercise exercise = service.find(requestAssessment.getExerciseId());

        List<Answer> answers = getAnswers(requestAssessment, exercise);

        boolean check = answers
                .stream()
                .anyMatch(answer -> checkTranslateAnswer(requestAssessment.getAnswerLetter(), answer.getContent()));

        return ResponseAssessment.builder()
                .status(check ? EStatus.RIGHT : EStatus.WRONG)
                .sentence(requestAssessment.getSentence())
                .answerValidation(answers)
                .build();
    }

}
