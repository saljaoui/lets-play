package zone01.soufian.lets_play.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import zone01.soufian.lets_play.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
