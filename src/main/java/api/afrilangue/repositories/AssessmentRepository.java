package api.afrilangue.repositories;

import api.afrilangue.models.Assessment;
import api.afrilangue.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Repository
public interface AssessmentRepository extends MongoRepository<Assessment, String> {
    Assessment findByUser(User user);
}
