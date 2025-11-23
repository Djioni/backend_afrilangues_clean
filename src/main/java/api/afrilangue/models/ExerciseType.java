package api.afrilangue.models;

import lombok.Getter;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Getter
public enum ExerciseType {
    PUT_IN_ORDER,
    LISTEN,
    MATCH,
    MEMORY,
    DIALOGUE,
    SINGLE_CHOICE_QUESTION_AUDIO_FORMAT,
    SINGLE_CHOICE_QUESTION_IMAGE_FORMAT,
    SINGLE_CHOICE_QUESTION_TEXT_FORMAT,
    TRANSLATE,
    TEXT_ZONE
}
