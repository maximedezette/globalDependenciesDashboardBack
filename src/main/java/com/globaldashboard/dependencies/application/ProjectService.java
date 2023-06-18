package com.globaldashboard.dependencies.application;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.Objective;
import com.globaldashboard.dependencies.domain.Project;
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

    private final ObjectiveService objectiveService;

    public ProjectService(ProjectRepository projectRepository, ObjectiveService objectiveService) {
        this.projectRepository = projectRepository;
        this.objectiveService = objectiveService;
    }

    public Set<Project> getAllProjects() {
        return this.projectRepository.findAll();
    }

    @Transactional
    public void deleteByName(String projectName) {
        this.projectRepository.deleteByName(projectName);
    }

    public Project getProjectByName(String name) {
        return this.projectRepository.findByName(name);
    }

    public Map<Objective, Boolean> getProjectStatus(String name) {
        Project project = this.projectRepository.findByName(name);
        Collection<Objective> objectives = this.objectiveService.getAllObjectives();
        List<Dependency> dependencies = project.dependencies();

        return objectives.stream()
                .collect(Collectors.toMap(Function.identity(),
                        isAchievedBy(dependencies)
                ));
    }

    private Function<Objective, Boolean> isAchievedBy(List<Dependency> dependencies) {
        return objective -> dependencies.stream()
                .anyMatch(objective::isAchievedBy);
    }

    public void save(Project project) {
        this.projectRepository.save(project);
    }
}
