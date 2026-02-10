package zone01.soufian.lets_play.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(

    @NotBlank(message = "Username or email is required")
    @Size(min = 3, max = 254, message = "Username or email must be between 3 and 254 characters")
    String username,

    @Email(message = "Email must be valid")
    @Size(max = 254, message = "Email must be at most 254 characters")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
    String password

) {}
