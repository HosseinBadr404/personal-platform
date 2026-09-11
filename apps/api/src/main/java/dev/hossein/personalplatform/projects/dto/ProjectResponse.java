package dev.hossein.personalplatform.projects.dto;

import dev.hossein.personalplatform.projects.Project;
import dev.hossein.personalplatform.projects.ProjectStatus;
import dev.hossein.personalplatform.projects.ProjectType;

import java.time.Instant;
import java.util.UUID;

public record ProjectResponse(
        UUID id,
        String title,
        String slug,
        String shortDescription,
        String description,
        ProjectStatus status,
        ProjectType type,
        String repositoryUrl,
        String liveUrl,
        boolean featured,
        Instant createdAt,
        Instant updatedAt,
        Instant publishedAt
) {
    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getSlug(),
                project.getShortDescription(),
                project.getDescription(),
                project.getStatus(),
                project.getType(),
                project.getRepositoryUrl(),
                project.getLiveUrl(),
                project.isFeatured(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                project.getPublishedAt()
        );
    }
}
