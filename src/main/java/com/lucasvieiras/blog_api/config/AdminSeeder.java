package com.lucasvieiras.blog_api.config;

import com.lucasvieiras.blog_api.entities.User;
import com.lucasvieiras.blog_api.enums.Role;
import com.lucasvieiras.blog_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:}")
    private String adminUsername;

    @Value("${app.admin.email:}")
    private String adminEmail;

    @Value("${app.admin.password:}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        seedAdminUser();
    }

    private void seedAdminUser() {
        if (!StringUtils.hasText(adminUsername)
                || !StringUtils.hasText(adminEmail)
                || !StringUtils.hasText(adminPassword)) {
            log.debug("Admin seeder skipped: environment variables not set");
            return;
        }

        if (userRepository.existsByRole(Role.ADMIN)) {
            log.debug("Admin seeder skipped: admin user already exists");
            return;
        }

        if (userRepository.existsByUsername(adminUsername)) {
            log.warn("Admin seeder failed: username '{}' already exists", adminUsername);
            return;
        }

        if (userRepository.existsByEmail(adminEmail)) {
            log.warn("Admin seeder failed: email '{}' already exists", adminEmail);
            return;
        }

        User admin = User.builder()
                .username(adminUsername)
                .email(adminEmail)
                .hashedPassword(passwordEncoder.encode(adminPassword))
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);

        log.info("Admin user created successfully: {}", adminUsername);
    }
}
