package zone01.soufian.lets_play.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import zone01.soufian.lets_play.model.Role;

public record UserUpdateRequest(

    @Size(min = 3, max = 254, message = "Username must be between 3 and 254 characters")
    String username,

    @Email(message = "Email must be valid")
    @Size(max = 254, message = "Email must be at most 254 characters")
    String email,

    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    String password,

    Role role

) {}
