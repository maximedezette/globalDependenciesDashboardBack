package com.globaldashboard.application;

import com.globaldashboard.domain.Project;
import com.globaldashboard.domain.port.secondary.ProjectRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Set<Project> getAllProjects(){
        return this.projectRepository.findAll();
    }

    public void save(Project project) {
        this.projectRepository.save(project);
    }
    @Transactional
    public void deleteByName(String projectName) {
        this.projectRepository.deleteByName(projectName);
    }

    public Project getProjectByName(String name) {
        return this.projectRepository.findByName(name);
    }
}
