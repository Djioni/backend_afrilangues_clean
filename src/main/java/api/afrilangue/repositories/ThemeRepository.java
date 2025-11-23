package api.afrilangue.repositories;

import api.afrilangue.models.Language;
import api.afrilangue.models.Theme;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Repository
public interface ThemeRepository extends MongoRepository<Theme,String> {
    List<Theme> findByLanguage(Language language);
    boolean existsByName(String name);
}
