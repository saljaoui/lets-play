package zone01.soufian.lets_play.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import zone01.soufian.lets_play.dto.auth.AuthResponse;
import zone01.soufian.lets_play.dto.auth.AuthRequest;
import zone01.soufian.lets_play.dto.user.UserResponse;
import zone01.soufian.lets_play.model.Role;
import zone01.soufian.lets_play.model.User;
import zone01.soufian.lets_play.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        String token = authService.login(request.username(), request.password());
        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody AuthRequest request) {
        User user = User.builder()
            .username(request.username())
            .email(request.email())
            .password(request.password())
            .role(Role.USER)
            .build();
        User saved = authService.register(user);
        return ResponseEntity.ok(new UserResponse(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getRole()));
    }
}
