package api.afrilangue.repositories;

import api.afrilangue.models.Language;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Repository
public interface LanguageRepository extends MongoRepository<Language, String> {
    @Query("{ 'name' : { $regex: ?0, $options: 'i' } }")
    Optional<Language> findByNameIgnoreCase(String name);

boolean existsLanguageByNameIgnoreCase(String name);
}
