package api.afrilangue.serrvices.assessment;

import api.afrilangue.models.ExerciseType;
import api.afrilangue.models.RequestAssessment;
import api.afrilangue.models.ResponseAssessment;
import api.afrilangue.models.User;
import api.afrilangue.serrvices.user.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
public class AssessmentServiceFactory {
    final Map<ExerciseType, AssessmentFactory> assessmentFactory;
    private final UserInfoService userInfoService;

    public AssessmentServiceFactory(CheckBasicAssessment basicAssessment,
                                    CheckDialogueAssessment dialogueAssessment,
                                    OrderedAssessmentService orderedAssessment,
                                    CheckMemoryGameAssessment memoryGameAssessment,
                                    TranslationAssessment translationAssessment,
                                    UserInfoService userInfoService) {

        this.assessmentFactory = Map.of(
                ExerciseType.LISTEN, basicAssessment,
                ExerciseType.SINGLE_CHOICE_QUESTION_TEXT_FORMAT, basicAssessment,
                ExerciseType.MATCH, basicAssessment,
                ExerciseType.TRANSLATE, translationAssessment,
                ExerciseType.DIALOGUE, dialogueAssessment,
                ExerciseType.SINGLE_CHOICE_QUESTION_IMAGE_FORMAT, basicAssessment,
                ExerciseType.SINGLE_CHOICE_QUESTION_AUDIO_FORMAT, basicAssessment,
                ExerciseType.MEMORY, memoryGameAssessment,
                ExerciseType.PUT_IN_ORDER, orderedAssessment
        );
        this.userInfoService = userInfoService;

    }

    public ResponseAssessment performAssessment(RequestAssessment requestAssessment) {
        ResponseAssessment responseAssessment = assessmentFactory.get(requestAssessment.getType())
                .checkAssessment(requestAssessment);
        User user = userInfoService.update(requestAssessment.getUserId(), responseAssessment.getStatus());
        responseAssessment.setIsNumberPoint(user.getIsNumberPoint());
        return responseAssessment;
    }
}
