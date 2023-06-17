package com.globaldashboard.dependencies.application;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.Objective;
import com.globaldashboard.dependencies.domain.ProjectDescription;
import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.domain.port.secondary.PomHttpRetriever;
import com.globaldashboard.dependencies.domain.port.secondary.ProjectRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final PomHttpRetriever pomHttpRetriever;

    private final ObjectiveService objectiveService;

    public ProjectService(ProjectRepository projectRepository, PomHttpRetriever pomHttpRetriever, ObjectiveService objectiveService) {
        this.projectRepository = projectRepository;
        this.pomHttpRetriever = pomHttpRetriever;
        this.objectiveService = objectiveService;
    }

    public Set<ProjectDescription> getAllProjects() {
        return this.projectRepository.findAll();
    }

    @Transactional
    public void deleteByName(String projectName) {
        this.projectRepository.deleteByName(projectName);
    }

    public ProjectDescription getProjectByName(String name) {
        return this.projectRepository.findByName(name);
    }

    public Map<Objective, Boolean> getProjectStatus(String name) {
        ProjectDescription project = this.projectRepository.findByName(name);
        Project projectInformation = this.pomHttpRetriever.getFromURL(project.pomURL());
        Collection<Objective> objectives = this.objectiveService.getAllObjectives();
        List<Dependency> dependencies = projectInformation.dependencies();

        return objectives.stream()
                .collect(Collectors.toMap(Function.identity(),
                        isAchievedBy(dependencies)
                ));
    }

    private Function<Objective, Boolean> isAchievedBy(List<Dependency> dependencies) {
        return objective -> dependencies.stream()
                .anyMatch(objective::isAchievedBy);
    }

    public void save(Project pom, ProjectDescription projectDescription) {
        this.projectRepository.save(pom, projectDescription);
    }
}
