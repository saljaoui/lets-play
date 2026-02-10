package zone01.soufian.lets_play.dto.user;

import zone01.soufian.lets_play.model.Role;

public record UserRequest(
    String username, 
    String email,
    String password, 
    Role role
) {}
