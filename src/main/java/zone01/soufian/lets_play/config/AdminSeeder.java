package zone01.soufian.lets_play.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log =
            LoggerFactory.getLogger(AdminSeeder.class);

    @Bean
    ApplicationRunner seedAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.seed-admin.email}") String email,
            @Value("${app.seed-admin.username}") String username,
            @Value("${app.seed-admin.password}") String password
    ) {

        return args -> {
            if (username == null || username.isBlank()
                    || password == null || password.isBlank()
                    || email == null || email.isBlank()) {
                log.warn("Admin seeding skipped variables.");
                return;
            }

            if (userRepository.existsByUsername(username)) {
                log.info("Admin user '{}' already exists. Skipping seeding.", username);
                return;
            }

            User admin = new User();
            admin.setUsername(username);
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            log.info("✅ Default admin user '{}' created successfully.", username);
        };
    }
}
