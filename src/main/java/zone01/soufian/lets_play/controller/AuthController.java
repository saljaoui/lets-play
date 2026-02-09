package zone01.soufian.lets_play.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zone01.soufian.lets_play.dto.auth.AuthResponse;
import zone01.soufian.lets_play.dto.auth.LoginRequest;
import zone01.soufian.lets_play.dto.auth.RegisterRequest;
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
    public AuthResponse login(@RequestBody LoginRequest request) {
        String token = authService.login(request.username(), request.password());
        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        User user = User.builder()
            .username(request.username())
            .password(request.password())
            .role(Role.ADMIN)
            .build();
        User saved = authService.register(user);
        return ResponseEntity.ok(new UserResponse(saved.getId(), saved.getUsername(), saved.getRole()));
    }
}
