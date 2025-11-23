package api.afrilangue.repositories;

import api.afrilangue.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    @Query("{'$or' : [{'username' : ?0},{'email' : ?0}]}")
    Optional<User> findByUsernameOrEmail(String username);
    Optional<User> findByEmail(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);



}
