package zone01.soufian.lets_play.dto.user;

import zone01.soufian.lets_play.model.Role;

public record UserResponse(
    String id,
    String username, 
    String email,
    Role role
) {}
