package zone01.soufian.lets_play.dto.auth;

import zone01.soufian.lets_play.model.Role;

public record RegisterRequest(
    String username,
    String password,
    Role role
) {}
