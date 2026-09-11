package dev.hossein.personalplatform.projects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    private UUID id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, unique = true, length = 160)
    private String slug;

    @Column(name = "short_description", nullable = false, length = 240)
    private String shortDescription;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private ProjectStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private ProjectType type;

    @Column(name = "repository_url", columnDefinition = "text")
    private String repositoryUrl;

    @Column(name = "live_url", columnDefinition = "text")
    private String liveUrl;

    @Column(nullable = false)
    private boolean featured;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "published_at")
    private Instant publishedAt;

    protected Project() {
    }

    private Project(
            String title,
            String shortDescription,
            String description,
            ProjectType type,
            String repositoryUrl,
            String liveUrl
    ) {
        Instant now = Instant.now();
        this.id = UUID.randomUUID();
        this.title = requireText(title, "Project title cannot be empty");
        this.slug = slugify(this.title) + "-" + this.id.toString().substring(0, 8);
        this.shortDescription = requireText(shortDescription, "Short description cannot be empty");
        this.description = requireText(description, "Description cannot be empty");
        this.type = requireType(type);
        this.repositoryUrl = blankToNull(repositoryUrl);
        this.liveUrl = blankToNull(liveUrl);
        this.status = ProjectStatus.DRAFT;
        this.featured = false;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public static Project create(
            String title,
            String shortDescription,
            String description,
            ProjectType type,
            String repositoryUrl,
            String liveUrl
    ) {
        return new Project(title, shortDescription, description, type, repositoryUrl, liveUrl);
    }

    public void updateDetails(
            String title,
            String shortDescription,
            String description,
            ProjectType type,
            String repositoryUrl,
            String liveUrl
    ) {
        this.title = requireText(title, "Project title cannot be empty");
        this.shortDescription = requireText(shortDescription, "Short description cannot be empty");
        this.description = requireText(description, "Description cannot be empty");
        this.type = requireType(type);
        this.repositoryUrl = blankToNull(repositoryUrl);
        this.liveUrl = blankToNull(liveUrl);
        this.updatedAt = Instant.now();
    }

    public void publish() {
        if (status == ProjectStatus.ARCHIVED) {
            throw new IllegalStateException("Archived projects cannot be published directly");
        }
        if (liveUrl == null && repositoryUrl == null) {
            throw new IllegalStateException("A project needs a live URL or repository URL before publishing");
        }

        this.status = ProjectStatus.PUBLISHED;
        this.publishedAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    void touchUpdatedAt() {
        this.updatedAt = Instant.now();
    }

    private static ProjectType requireType(ProjectType type) {
        if (type == null) {
            throw new IllegalArgumentException("Project type is required");
        }
        return type;
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private static String slugify(String value) {
        String slug = value.toLowerCase(Locale.ROOT)
                .trim()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");

        return slug.isBlank() ? "project" : slug;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getSlug() { return slug; }
    public String getShortDescription() { return shortDescription; }
    public String getDescription() { return description; }
    public ProjectStatus getStatus() { return status; }
    public ProjectType getType() { return type; }
    public String getRepositoryUrl() { return repositoryUrl; }
    public String getLiveUrl() { return liveUrl; }
    public boolean isFeatured() { return featured; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Instant getPublishedAt() { return publishedAt; }
}
