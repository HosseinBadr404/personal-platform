package dev.hossein.personalplatform.projects;

import dev.hossein.personalplatform.projects.dto.CreateProjectRequest;
import dev.hossein.personalplatform.projects.dto.ProjectResponse;
import dev.hossein.personalplatform.projects.dto.UpdateProjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public ProjectResponse create(CreateProjectRequest request) {
        Project project = Project.create(
                request.title(),
                request.shortDescription(),
                request.description(),
                request.type(),
                request.repositoryUrl(),
                request.liveUrl()
        );

        return ProjectResponse.from(projectRepository.save(project));
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> findAll() {
        return projectRepository.findAll().stream()
                .map(ProjectResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjectResponse findById(UUID id) {
        return ProjectResponse.from(findEntity(id));
    }

    @Transactional
    public ProjectResponse update(UUID id, UpdateProjectRequest request) {
        Project project = findEntity(id);
        project.updateDetails(
                request.title(),
                request.shortDescription(),
                request.description(),
                request.type(),
                request.repositoryUrl(),
                request.liveUrl()
        );
        return ProjectResponse.from(project);
    }

    @Transactional
    public ProjectResponse publish(UUID id) {
        Project project = findEntity(id);
        project.publish();
        return ProjectResponse.from(project);
    }

    @Transactional
    public void delete(UUID id) {
        Project project = findEntity(id);
        projectRepository.delete(project);
    }

    private Project findEntity(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }
}
