package api.afrilangue.repositories;

import api.afrilangue.models.Subscription;
import api.afrilangue.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    List<Subscription> findSubscriptionByUser(User user);

}
