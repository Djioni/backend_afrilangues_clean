package api.afrilangue.serrvices.assessment;

import api.afrilangue.models.Answer;
import api.afrilangue.models.AnswerLetter;
import api.afrilangue.models.Exercise;
import api.afrilangue.models.RequestAssessment;

import java.util.List;

import static api.afrilangue.helper.Utils.normalizeText;
import static api.afrilangue.helper.Utils.removeParenthesesAndContent;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public class AssessmentUtils {

    public static List<Answer> getAnswers(RequestAssessment requestAssessment, Exercise exercise) {
        List<Answer> answers = exercise.getExerciseAndAnswers().stream()
                .filter(exerciseAndAnswers -> exerciseAndAnswers.getSentence().equalsIgnoreCase(requestAssessment.getSentence()))
                .findAny().orElseGet(() -> {
                    throw new IllegalArgumentException("La phrase " + requestAssessment.getSentence() + " ne corresponde à aucune question enregistrée ");
                }).getAnswers();

        return answers.stream().filter(answer -> answer.isCorrect() == true)
                .toList();
    }

    public static String getOrderedAnswers(RequestAssessment requestAssessment, Exercise exercise) {
        return exercise.getExerciseAndAnswers().stream()
                .filter(exerciseAndAnswers -> exerciseAndAnswers.getSentence().equalsIgnoreCase(requestAssessment.getSentence()))
                .findAny().orElseGet(() -> {
                    throw new IllegalArgumentException("La phrase " + requestAssessment.getSentence() + " ne corresponde à aucune question enregistrée ");
                }).getSentence();
    }

    public static boolean checkAnswer(List<AnswerLetter> answerLetter, String response) {
        return answerLetter.stream().anyMatch(answer -> {
            String content = answer.getContent().replace(" ", "");
            String responseWithoutSpace = response.replace(" ", "");
            return normalizeText(content).equalsIgnoreCase(normalizeText(responseWithoutSpace));
        });
    }

    public static boolean checkTranslateAnswer(List<AnswerLetter> answerLetter, String response) {
        return answerLetter.stream().anyMatch(answer -> {
            String content = removeParenthesesAndContent(answer.getContent()).replaceAll("\\p{Punct}", "")
                    .replace(" ", "");
            String responseWithoutSpace =removeParenthesesAndContent(response)
                    .replaceAll("\\p{Punct}", "")
                    .replace(" ", "");
            return normalizeText(content).equalsIgnoreCase(normalizeText(responseWithoutSpace));
        });
    }


    static boolean checkQCMMediaAnswer(List<AnswerLetter> answerLetter, String media) {
        return answerLetter.stream()
                .anyMatch(answer -> answer.getContent().equalsIgnoreCase(media));
    }

}
