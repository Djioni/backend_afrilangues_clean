package api.afrilangue.repositories;

import api.afrilangue.models.Lesson;
import api.afrilangue.models.Theme;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Repository
public interface LessonRepository extends MongoRepository<Lesson, String> {
    List<Lesson> findByTheme(Theme theme);

}
