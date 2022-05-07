package gr9.eventmarket.database.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import gr9.eventmarket.database.model.ERole;
import gr9.eventmarket.database.model.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole role);
}
