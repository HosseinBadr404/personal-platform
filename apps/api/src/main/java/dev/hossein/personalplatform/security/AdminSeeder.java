package dev.hossein.personalplatform.security;

import dev.hossein.personalplatform.users.AppUser;
import dev.hossein.personalplatform.users.AppUserRepository;
import dev.hossein.personalplatform.users.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements ApplicationRunner {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminPassword;

    public AdminSeeder(
            AppUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.email:}") String adminEmail,
            @Value("${app.admin.password:}") String adminPassword
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (adminEmail.isBlank() || adminPassword.isBlank()) {
            return;
        }

        if (userRepository.existsByEmailIgnoreCase(adminEmail)) {
            return;
        }

        userRepository.save(new AppUser(
                adminEmail,
                passwordEncoder.encode(adminPassword),
                Role.ADMIN
        ));
    }
}
