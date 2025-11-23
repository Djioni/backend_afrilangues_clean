package api.afrilangue.repositories;

import api.afrilangue.models.Exercise;
import api.afrilangue.models.ExerciseStatus;
import api.afrilangue.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public interface ExerciseStatusRepository extends MongoRepository<ExerciseStatus, String> {
    ExerciseStatus findExerciseStatusByUserAndExercise(User user, Exercise exercise);
    List<ExerciseStatus> findExerciseStatusByUser(User user);
    boolean existsExerciseStatusByUserAndExercise(User user, Exercise exercise);
}
