package api.afrilangue.serrvices.assessment;

import api.afrilangue.models.Answer;
import api.afrilangue.models.EStatus;
import api.afrilangue.models.Exercise;
import api.afrilangue.models.ExerciseAndAnswers;
import api.afrilangue.models.RequestAssessment;
import api.afrilangue.models.ResponseAssessment;
import api.afrilangue.serrvices.exercice.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.IntStream;

import static api.afrilangue.helper.Utils.normalizeText;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class CheckDialogueAssessment implements AssessmentFactory {
    private final ExerciseService service;

    @Override
    public ResponseAssessment checkAssessment(RequestAssessment requestAssessment) {
        Exercise exercise = service.find(requestAssessment.getExerciseId());


        Set<ExerciseAndAnswers> exerciseAndAnswers = exercise.getExerciseAndAnswers();

        String resultQuestion = exerciseAndAnswers.stream()
                .filter(exerciseRepository -> requestAssessment.getOrder().equals(exerciseRepository.getOrder()))
                .map(exo1 -> {
                    String chaine = exo1.getSentence();
                    List<String> mots = exo1.getQuestionWords();
                    int index = 0;
                    while (chaine.contains("...") && index < mots.size()) {
                        chaine = chaine.replaceFirst("\\.\\.\\.", mots.get(index));
                        index++;
                    }
                    exo1.setSentence(chaine);
                    return exo1;

                }).map(ExerciseAndAnswers::getSentence)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No matching exercise found"));

        List<Answer> reconstructAnswers = reconstructAnswer(requestAssessment.getOrder(), exerciseAndAnswers);


        boolean checkResponse = reconstructAnswers.size() == requestAssessment.getAnswerLetter().size() &&
                IntStream.range(0, reconstructAnswers.size())
                        .anyMatch(i -> {
                            String questionJoin = reconstructAnswers.get(i).getContent().replace(" ", "");
                            String responseJoin = requestAssessment.getAnswerLetter().get(i).getContent().replace(" ", "");
                            return normalizeText(questionJoin).equalsIgnoreCase(normalizeText(responseJoin));
                        });

        boolean check = compare(resultQuestion, requestAssessment.getSentence()) && checkResponse;


        return ResponseAssessment.builder()
                .status(check ? EStatus.RIGHT : EStatus.WRONG)
                .sentence(resultQuestion)
                .answerValidation(reconstructAnswers)
                .build();
    }

    private List<Answer> reconstructAnswer(Integer order, Set<ExerciseAndAnswers> exerciseAndAnswers) {
        return exerciseAndAnswers
                .stream()
                .filter(exo -> order.equals(exo.getOrder()))
                .flatMap(exo1 -> exo1.getAnswers().stream())
                .map(answer -> {
                    String chaine = answer.getContent();
                    List<String> mots = answer.getWords();

                    int index = 0;
                    while (chaine.contains("...") && index < mots.size()) {
                        chaine = chaine.replaceFirst("\\.\\.\\.", mots.get(index));
                        index++;
                    }

                    answer.setContent(chaine);

                    return answer;
                })
                .toList();


    }


    public boolean compare(String question, String response) {
        String questionJoin = question.replace(" ", "");
        String responseJoin = response.replace(" ", "");
        return normalizeText(questionJoin).equalsIgnoreCase(normalizeText(responseJoin));
    }

}
