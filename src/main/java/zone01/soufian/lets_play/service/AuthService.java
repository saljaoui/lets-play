package zone01.soufian.lets_play.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zone01.soufian.lets_play.model.User;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;

    public String login(String username, String password) {
        // TODO: verify credentials and issue JWT
        return "dummy-token";
    }

    public User register(User user) {
        // TODO: hash password and set default role
        return userService.save(user);
    }
}
