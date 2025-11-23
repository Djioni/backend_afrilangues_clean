package api.afrilangue.repositories;


import api.afrilangue.models.PasswordConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author Ibrahima Diallo <ibrahima.diallo2@supinfo.com>
 */
public interface IpasswordConfirmationToken extends MongoRepository<PasswordConfirmationToken, String> {
    Optional <PasswordConfirmationToken> findByToken(String token);
}
