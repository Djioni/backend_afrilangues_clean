package api.afrilangue.repositories;

import api.afrilangue.models.Lesson;
import api.afrilangue.models.LessonSection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Repository
public interface LessonSectionRepository extends MongoRepository<LessonSection, String> {
    List<LessonSection> findByLesson(Lesson lesson);
}
