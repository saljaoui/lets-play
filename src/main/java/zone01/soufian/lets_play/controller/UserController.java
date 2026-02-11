package zone01.soufian.lets_play.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import zone01.soufian.lets_play.dto.user.UserCreateRequest;
import zone01.soufian.lets_play.dto.user.UserResponse;
import zone01.soufian.lets_play.dto.user.UserUpdateRequest;
import zone01.soufian.lets_play.model.User;
import zone01.soufian.lets_play.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponse> list() {
        return userService.findAll().stream()
            .map(UserController::toResponse)
            .toList();
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable String id) {
        User user = userService.findById(id);
        return toResponse(user);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        User user = User.builder()
            .username(request.username())
            .email(request.email())
            .password(request.password())
            .role(request.role())
            .build();
        User saved = userService.create(user);
        return ResponseEntity.ok(toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable String id,
            @Valid @RequestBody UserUpdateRequest request,
            @AuthenticationPrincipal User actor) {
        User updated = userService.update(id, request, actor);
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, @AuthenticationPrincipal User actor) {
        userService.delete(id, actor);
        return ResponseEntity.noContent().build();
    }

    private static UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }
}
