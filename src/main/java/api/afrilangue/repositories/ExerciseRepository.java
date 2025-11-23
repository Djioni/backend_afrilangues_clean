package api.afrilangue.repositories;

import api.afrilangue.models.Exercise;
import api.afrilangue.models.LessonSection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public interface ExerciseRepository extends MongoRepository<Exercise, String> {
    List<Exercise> findByLessonSectionOrderByCreatedAtAsc(LessonSection lessonSection);

    List<Exercise> findAllByOrderByCreatedAtAsc();
}
