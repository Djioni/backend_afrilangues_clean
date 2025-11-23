package api.afrilangue.serrvices.assessment;

import api.afrilangue.models.*;
import api.afrilangue.serrvices.exercice.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static api.afrilangue.helper.Utils.normalizeText;
import static api.afrilangue.serrvices.assessment.AssessmentUtils.*;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class OrderedAssessmentService implements AssessmentFactory {

    private final ExerciseService service;


    @Override
    public ResponseAssessment checkAssessment(RequestAssessment requestAssessment) {
        Exercise exercise = service.find(requestAssessment.getExerciseId());

        String sentence = getOrderedAnswers(requestAssessment, exercise);

        boolean check = requestAssessment.getAnswerLetter()
                .stream()
                .anyMatch(answer -> {
                    String questionJoin = sentence.replace(" ", "");
                    String responseJoin = answer.getContent().replace(" ", "");
                    return normalizeText(questionJoin).equalsIgnoreCase(normalizeText(responseJoin));
                });

        return ResponseAssessment.builder()
                .status(check ? EStatus.RIGHT : EStatus.WRONG)
                .sentence(requestAssessment.getSentence())
                .answerValidation(List.of(Answer.builder()
                        .content(sentence)
                        .isCorrect(true)
                        .build()))
                .build();
    }
}
