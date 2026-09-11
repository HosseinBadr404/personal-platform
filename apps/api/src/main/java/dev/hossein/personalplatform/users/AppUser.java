package dev.hossein.personalplatform.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "app_users")
public class AppUser {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private Role role;

    protected AppUser() {
    }

    public AppUser(String email, String passwordHash, Role role) {
        this.id = UUID.randomUUID();
        this.email = normalizeEmail(email);
        this.passwordHash = requireText(passwordHash, "Password hash is required");
        this.role = role == null ? Role.ADMIN : role;
    }

    private static String normalizeEmail(String value) {
        return requireText(value, "Email is required").toLowerCase(Locale.ROOT);
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    public void rotatePasswordHash(String passwordHash) {
        this.passwordHash = requireText(passwordHash, "Password hash is required");
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
}
