package zone01.soufian.lets_play.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import zone01.soufian.lets_play.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
