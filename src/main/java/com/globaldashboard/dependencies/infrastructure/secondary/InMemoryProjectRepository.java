package com.globaldashboard.dependencies.infrastructure.secondary;


import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.domain.port.secondary.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryProjectRepository implements ProjectRepository {

    private final ProjectSpringRepository projectSpringRepository;

    @Autowired
    public InMemoryProjectRepository(ProjectSpringRepository projectSpringRepository) {
        this.projectSpringRepository = projectSpringRepository;
    }

    @Override
    public Project findByName(String name) {
        return this.projectSpringRepository.findByName(name).toDomain();
    }

    @Override
    public void deleteByName(String name) {
        this.projectSpringRepository.deleteByName(name);
    }

    @Override
    public List<Project> findAll() {
        return this.projectSpringRepository.findAll()
                .stream()
                .map(ProjectEntity::toDomain)
                .toList();
    }

    @Override
    public void save(Project project) {
        ProjectEntity projectEntity = ProjectEntity.from(project);

        this.projectSpringRepository.save(projectEntity);
    }
}
