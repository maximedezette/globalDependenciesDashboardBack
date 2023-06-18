package com.globaldashboard.dependencies.infrastructure.secondary;


import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.domain.ProjectDescription;
import com.globaldashboard.dependencies.domain.port.secondary.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class InMemoryProjectRepository implements ProjectRepository {

    private final ProjectSpringRepository projectSpringRepository;

    @Autowired
    public InMemoryProjectRepository(ProjectSpringRepository projectSpringRepository) {
        this.projectSpringRepository = projectSpringRepository;
    }

    @Override
    public ProjectDescription findByName(String name) {
        return this.projectSpringRepository.findByName(name).toDomain();
    }

    @Override
    public void deleteByName(String name) {
        this.projectSpringRepository.deleteByName(name);
    }

    @Override
    public Set<ProjectDescription> findAll() {
        return this.projectSpringRepository.findAll()
                .stream()
                .map(ProjectEntity::toDomain)
                .collect(Collectors.toSet());
    }
    @Override
    public void save(Project project, ProjectDescription projectDescription) {
        Set<DependencyEntity> dependencies = getDependencies(project);
        ProjectEntity projectEntity = getProjectEntity(project, projectDescription, dependencies);

        this.projectSpringRepository.save(projectEntity);
    }

    private static ProjectEntity getProjectEntity(Project project, ProjectDescription projectDescription, Set<DependencyEntity> dependencies) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(projectDescription.name());
        projectEntity.setPomURL(projectDescription.pomURL());
        projectEntity.setDependencies(dependencies);
        projectEntity.setJavaVersion(project.java());
        projectEntity.setDescription(project.description());
        projectEntity.setVersion(project.projectVersion().readableValue());
        return projectEntity;
    }

    private static Set<DependencyEntity> getDependencies(Project project) {
        return project.dependencies()
                .stream()
                .map(DependencyEntity::from)
                .collect(Collectors.toSet());
    }
}
