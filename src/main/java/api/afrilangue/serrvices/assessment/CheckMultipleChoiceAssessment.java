package api.afrilangue.serrvices.assessment;

import api.afrilangue.models.*;
import api.afrilangue.serrvices.exercice.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

import static api.afrilangue.serrvices.assessment.AssessmentUtils.getAnswers;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class CheckMultipleChoiceAssessment implements AssessmentFactory {
    private final ExerciseService service;


    @Override
    public ResponseAssessment checkAssessment(RequestAssessment requestAssessment) {
        Exercise exercise = service.find(requestAssessment.getExerciseId());

        List<Answer> answers = getAnswers(requestAssessment, exercise);

        boolean allMatch = answers.size() == requestAssessment.getAnswerLetter().size() &&
                IntStream.range(0, answers.size())
                        .allMatch(i -> answers.get(i).getContent().equalsIgnoreCase(requestAssessment.getAnswerLetter().get(i).getContent()));

        return ResponseAssessment.builder()
                .status(allMatch ? EStatus.RIGHT : EStatus.WRONG)
                .sentence(requestAssessment.getSentence())
                .answerValidation(answers)
                .build();
    }
}
