package api.afrilangue.serrvices.assessment;

import api.afrilangue.models.*;
import api.afrilangue.serrvices.exercice.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static api.afrilangue.serrvices.assessment.AssessmentUtils.checkQCMMediaAnswer;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class CheckMemoryGameAssessment implements AssessmentFactory {
    private final ExerciseService service;


    @Override
    public ResponseAssessment checkAssessment(RequestAssessment requestAssessment) {
        Exercise exercise = service.find(requestAssessment.getExerciseId());

        List<ExerciseMedia> media = exercise.getExerciseAndAnswers()
                .stream()
                .filter(exerciseAndAnswer -> exerciseAndAnswer.getSentence().equalsIgnoreCase(requestAssessment.getSentence()))
                .flatMap(exerciseMedia -> exerciseMedia.getMediaQuestion().stream())
                .filter(exo -> exo.getType().startsWith("image")).toList();


        boolean check = media
                .stream()
                .allMatch(answer -> checkQCMMediaAnswer(requestAssessment.getAnswerLetter(), answer.getMedia()));

        return ResponseAssessment.builder()
                .status(check ? EStatus.RIGHT : EStatus.WRONG)
                .sentence(requestAssessment.getSentence())
                .answerValidation(null)
                .build();
    }

}
