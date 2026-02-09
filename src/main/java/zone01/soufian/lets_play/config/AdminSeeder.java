package zone01.soufian.lets_play.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import zone01.soufian.lets_play.model.User;
import zone01.soufian.lets_play.model.Role;
import zone01.soufian.lets_play.repository.UserRepository;

@Configuration
public class AdminSeeder {

    @Bean
    ApplicationRunner seedAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.seed-admin.username}") String username,
            @Value("${app.seed-admin.password}") String password
    ) {

        return args -> {

            if (userRepository.existsByUsername(username)) {
                System.out.println("ℹ️ Admin already exists, skipping seeding.");
                return;
            }

            User admin = new User();
            admin.setUsername(username);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        };
    }
}

