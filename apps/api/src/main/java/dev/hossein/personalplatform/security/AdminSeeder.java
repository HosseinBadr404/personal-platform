package dev.hossein.personalplatform.security;

import dev.hossein.personalplatform.users.AppUser;
import dev.hossein.personalplatform.users.AppUserRepository;
import dev.hossein.personalplatform.users.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AdminSeeder implements ApplicationRunner {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminPassword;
    private final String rotatePasswordTo;

    public AdminSeeder(
            AppUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.email:}") String adminEmail,
            @Value("${app.admin.password:}") String adminPassword,
            @Value("${app.admin.rotate-password-to:}") String rotatePasswordTo
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.rotatePasswordTo = rotatePasswordTo;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (adminEmail.isBlank()) {
            return;
        }

        AppUser existingAdmin = userRepository
                .findByEmailIgnoreCase(adminEmail)
                .orElse(null);

        if (existingAdmin == null) {
            if (adminPassword.isBlank()) {
                return;
            }

            userRepository.save(new AppUser(
                    adminEmail,
                    passwordEncoder.encode(adminPassword),
                    Role.ADMIN
            ));
            return;
        }

        if (!rotatePasswordTo.isBlank()) {
            existingAdmin.rotatePasswordHash(
                    passwordEncoder.encode(rotatePasswordTo)
            );
        }
    }
}
