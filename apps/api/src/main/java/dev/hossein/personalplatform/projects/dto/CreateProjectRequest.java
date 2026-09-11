package dev.hossein.personalplatform.projects.dto;

import dev.hossein.personalplatform.projects.ProjectType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProjectRequest(
        @NotBlank @Size(max = 120) String title,
        @NotBlank @Size(max = 240) String shortDescription,
        @NotBlank String description,
        @NotNull ProjectType type,
        String repositoryUrl,
        String liveUrl
) {
}
