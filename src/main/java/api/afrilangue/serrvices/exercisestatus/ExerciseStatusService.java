package api.afrilangue.serrvices.exercisestatus;

import api.afrilangue.dto.request.ExerciseStatusRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.ExerciseStatus;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public interface ExerciseStatusService {
    ExerciseStatus create(ExerciseStatusRequest exerciseStatus);

    ExerciseStatus find(String id);

    List<ExerciseStatus> findAll();

    DefaultResponse update(String id, ExerciseStatus exercise);

    DefaultResponse delete(String id);

    DefaultResponse findExerciseStatusByUser(String userId, String exerciseId);

    DefaultResponse findLessonSectionStatusByUser(String userId, String lessonSectionId);

    DefaultResponse findLessonByUser(String userId, String lessonId);

    DefaultResponse findThemeByUser(String userId, String themeId);


}
