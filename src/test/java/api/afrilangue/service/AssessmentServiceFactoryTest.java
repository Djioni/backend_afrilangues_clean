package api.afrilangue.service;

import api.afrilangue.AfrilangueApplicationTests;
import api.afrilangue.models.*;
import api.afrilangue.serrvices.assessment.AssessmentServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
class AssessmentServiceFactoryTest extends AfrilangueApplicationTests {
    @Autowired
    private AssessmentServiceFactory serviceFactory;

    @Test
    void test_dialogue_assessment() {
        RequestAssessment requestAssessment = RequestAssessment.builder()
                .order(0)
                .type(ExerciseType.DIALOGUE)
                .userId("6483bf982b42660e8f1c0fc1")
                .exerciseId("6503729c41bd45770962a19e")
                .sentence("I ni sɔgɔma")
                .answerLetter(List.of(AnswerLetter.builder()
                        .content("Nba! I ni sɔgɔma.")
                        .build()))
                .build();

        ResponseAssessment responseAssessment = serviceFactory.performAssessment(requestAssessment);

        assertThat(responseAssessment).isNotNull();
        assertThat(responseAssessment.getStatus()).isEqualTo(EStatus.RIGHT);

    }

    @Test
    void test_qcm_media_assessment() {
        RequestAssessment requestAssessment = RequestAssessment.builder()
                .order(0)
                .type(ExerciseType.SINGLE_CHOICE_QUESTION_TEXT_FORMAT)
                .userId("6483bf982b42660e8f1c0fc1")
                .exerciseId("65036ff741bd45770962a18a")
                .sentence("Comment s'appelle ce fruit?")
                .answerLetter(List.of(AnswerLetter.builder()
                        .content("Ananas")
                        .build()))
                .build();

        ResponseAssessment responseAssessment = serviceFactory.performAssessment(requestAssessment);

        assertThat(responseAssessment).isNotNull();
        assertThat(responseAssessment.getStatus()).isEqualTo(EStatus.RIGHT);

    }


    @Test
    void test_basic_qcm_assessment() {
        RequestAssessment requestAssessment = RequestAssessment.builder()
                .order(0)
                .type(ExerciseType.SINGLE_CHOICE_QUESTION_TEXT_FORMAT)
                .userId("6483bf982b42660e8f1c0fc1")
                .exerciseId("65036bf741bd45770962a16b")
                .sentence("Capitale du Mali")
                .answerLetter(List.of(AnswerLetter.builder()
                        .content("Bamako")
                        .build()))
                .build();

        ResponseAssessment responseAssessment = serviceFactory.performAssessment(requestAssessment);

        assertThat(responseAssessment).isNotNull();
        assertThat(responseAssessment.getStatus()).isEqualTo(EStatus.RIGHT);

    }

    @Test
    void test_completed_qcm_assessment() {
        RequestAssessment requestAssessment = RequestAssessment.builder()
                .order(0)
                .type(ExerciseType.SINGLE_CHOICE_QUESTION_TEXT_FORMAT)
                .userId("6483bf982b42660e8f1c0fc1")
                .exerciseId("6503705b41bd45770962a18d")
                .sentence("Ne .....Musa.")
                .answerLetter(List.of(AnswerLetter.builder()
                        .content("tɔgɔ")
                        .build()))
                .build();

        ResponseAssessment responseAssessment = serviceFactory.performAssessment(requestAssessment);

        assertThat(responseAssessment).isNotNull();
        assertThat(responseAssessment.getStatus()).isEqualTo(EStatus.RIGHT);

    }

    @Test
    void test_ordered_assessment() {
        RequestAssessment requestAssessment = RequestAssessment.builder()
                .order(0)
                .type(ExerciseType.PUT_IN_ORDER)
                .userId("6483bf982b42660e8f1c0fc1")
                .exerciseId("6503711241bd45770962a19b")
                .sentence("Lɛsogo ka di ne yé.")
                .answerLetter(List.of(AnswerLetter.builder()
                        .content("Lɛsogo ka di ne yé.")
                        .build()))
                .build();

        ResponseAssessment responseAssessment = serviceFactory.performAssessment(requestAssessment);

        assertThat(responseAssessment).isNotNull();
        assertThat(responseAssessment.getStatus()).isEqualTo(EStatus.RIGHT);


    }

    @Test
    void test_memory_game_assessment() {
        RequestAssessment requestAssessment = RequestAssessment.builder()
                .order(1)
                .type(ExerciseType.MEMORY)
                .userId("6483bf982b42660e8f1c0fc1")
                .exerciseId("6503730041bd45770962a1c0")
                .sentence("Keninge")
                .answerLetter(List.of(AnswerLetter.builder()
                                .content("650372dc41bd45770962a1a6")
                                .build(),
                        AnswerLetter.builder()
                                .content("650372dc41bd45770962a1a6")
                                .build()))
                .build();

        ResponseAssessment responseAssessment = serviceFactory.performAssessment(requestAssessment);

        assertThat(responseAssessment).isNotNull();
        assertThat(responseAssessment.getStatus()).isEqualTo(EStatus.RIGHT);
    }
}
