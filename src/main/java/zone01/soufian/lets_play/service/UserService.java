package zone01.soufian.lets_play.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mongodb.DuplicateKeyException;

import zone01.soufian.lets_play.dto.user.UserUpdateRequest;
import zone01.soufian.lets_play.exception.BadRequestException;
import zone01.soufian.lets_play.exception.ConflictException;
import zone01.soufian.lets_play.exception.NotFoundException;
import zone01.soufian.lets_play.model.Role;
import zone01.soufian.lets_play.model.User;
import zone01.soufian.lets_play.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PostAuthorize("hasRole('ADMIN')")
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));
    }

    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }

    public User save(User user) {

        try {

            String username = normalizeUsername(user.getUsername());
            if (username == null || username.isBlank()) {
                throw new BadRequestException("Username is required");
            }
            String email = normalizeEmail(user.getEmail());

            if (userRepository.existsByUsername(username)) {
                throw new ConflictException("Username already exists");
            }

            if (email != null && !email.isBlank()) {
                if (userRepository.existsByEmail(email)) {
                    throw new ConflictException("Email already exists");
                }
            }

            user.setUsername(username);
            user.setEmail(email);

            return userRepository.save(user);

        } catch (DuplicateKeyException e) {
            throw new ConflictException("Username or email already exists");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User update(String id, UserUpdateRequest request, User actor) {
        User user = findById(id);
        boolean isAdmin = actor.getRole() == Role.ADMIN;

        if (!isAdmin && !user.getId().equals(actor.getId())) {
            throw new AccessDeniedException("You are not allowed to update this user");
        }

        if (!isAdmin && request.role() != null) {
            throw new AccessDeniedException("You are not allowed to change roles");
        }

        if (request.username() != null) {
            String username = normalizeUsername(request.username());
            if (username == null || username.isBlank()) {
                throw new BadRequestException("Username is required");
            }
            if (!username.equals(user.getUsername()) && userRepository.existsByUsername(username)) {
                throw new ConflictException("Username already exists");
            }
            user.setUsername(username);
        }

        if (request.email() != null) {
            String email = normalizeEmail(request.email());
            if (email != null && !email.equals(user.getEmail()) && userRepository.existsByEmail(email)) {
                throw new ConflictException("Email already exists");
            }
            user.setEmail(email);
        }

        if (request.password() != null) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }

        if (request.role() != null) {
            user.setRole(request.role());
        }

        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String id, User actor) {
        User user = findById(id);
        boolean isAdmin = actor.getRole() == Role.ADMIN;

        if (!isAdmin && !user.getId().equals(actor.getId())) {
            throw new AccessDeniedException("You are not allowed to delete this user");
        }

        userRepository.deleteById(id);
    }

    private String normalizeUsername(String username) {
        return username == null ? null : username.toLowerCase().trim();
    }

    private String normalizeEmail(String email) {
        if (email == null) {
            return null;
        }
        String normalized = email.toLowerCase().trim();
        return normalized.isBlank() ? null : normalized;
    }

}
