package api.afrilangue.repositories;

import api.afrilangue.models.ERole;
import api.afrilangue.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);

    Boolean existsRoleByName(ERole name);
}
