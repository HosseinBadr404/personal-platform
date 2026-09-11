package dev.hossein.personalplatform.projects;

import dev.hossein.personalplatform.projects.dto.CreateProjectRequest;
import dev.hossein.personalplatform.projects.dto.ProjectResponse;
import dev.hossein.personalplatform.projects.dto.UpdateProjectRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectResponse> findAll() {
        return projectService.findAll();
    }

    @GetMapping("/{id}")
    public ProjectResponse findById(@PathVariable UUID id) {
        return projectService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse create(@Valid @RequestBody CreateProjectRequest request) {
        return projectService.create(request);
    }

    @PatchMapping("/{id}")
    public ProjectResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProjectRequest request
    ) {
        return projectService.update(id, request);
    }

    @PostMapping("/{id}/publish")
    public ProjectResponse publish(@PathVariable UUID id) {
        return projectService.publish(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        projectService.delete(id);
    }
}
