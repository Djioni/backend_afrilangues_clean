package api.afrilangue.repositories;

import api.afrilangue.models.Advertisement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Repository
public interface AdvertisementRepository extends MongoRepository<Advertisement, String> {
}
