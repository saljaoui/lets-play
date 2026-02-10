package zone01.soufian.lets_play.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mongodb.DuplicateKeyException;

import lombok.RequiredArgsConstructor;
import zone01.soufian.lets_play.model.User;
import zone01.soufian.lets_play.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }

    public User save(User user) {

        try {

            String username = user.getUsername().toLowerCase().trim();
            String email = user.getEmail() == null ? null : user.getEmail().toLowerCase().trim();

            if (userRepository.existsByUsername(username)) {
                throw new IllegalArgumentException("Username already exists");
            }

            if (email != null && !email.isBlank()) {
                if (userRepository.existsByEmail(email)) {
                    throw new IllegalArgumentException("Email already exists");
                }
            }

            user.setUsername(username);
            user.setEmail(email);

            return userRepository.save(user);

        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("Username or email already exists");
        }
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }
}
