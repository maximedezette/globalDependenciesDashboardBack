package com.globaldashboard.application;

import com.globaldashboard.domain.Project;
import com.globaldashboard.domain.port.secondary.ProjectRepository;
import com.globaldashboard.infrastructure.secondary.ProjectEntity;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Set<Project> getAllProjects(){
        return this.projectRepository
                .findAll()
                .stream().map(ProjectEntity::toDomain)
                .collect(Collectors.toSet());
    }
}
