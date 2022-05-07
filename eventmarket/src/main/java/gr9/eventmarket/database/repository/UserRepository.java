package gr9.eventmarket.database.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import gr9.eventmarket.database.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
}
