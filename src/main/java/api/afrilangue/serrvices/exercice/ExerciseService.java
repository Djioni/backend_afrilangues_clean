package api.afrilangue.serrvices.exercice;

import api.afrilangue.dto.request.ExercisesRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.Exercise;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public interface ExerciseService {

    DefaultResponse create(ExercisesRequest exercise);

    Exercise find(String id);

    List<Exercise> findAll();

    DefaultResponse update(String id, Exercise exercise);

    DefaultResponse delete(String id);

    List<Exercise> findByLessonSection(String lessonSection);
}
