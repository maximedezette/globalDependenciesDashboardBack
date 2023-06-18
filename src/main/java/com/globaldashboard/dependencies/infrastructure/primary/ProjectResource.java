package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.application.ProjectService;
import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.domain.port.secondary.PomHttpRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("projects")
public class ProjectResource {

    private final ProjectService projectService;
    private final PomHttpRetriever pomHttpRetriever;

    @Autowired
    public ProjectResource(ProjectService projectService, PomHttpRetriever pomHttpRetriever) {
        this.projectService = projectService;
        this.pomHttpRetriever = pomHttpRetriever;
    }

    @GetMapping("simplified")
    public Collection<RestProjectDescription> getAllProjects() {
        return this.projectService.getAllProjects()
                .stream()
                .map(RestProjectDescription::from)
                .collect(Collectors.toSet());
    }

    @GetMapping("{name}/status")
    public Collection<RestAchievableObjective> getProjectStatus(@PathVariable String name) {
        return this.projectService.getProjectStatus(name)
                .entrySet()
                .stream()
                .map(entry -> RestAchievableObjective.from(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    @GetMapping
    public Set<RestProject> getAll() {
        Set<Project> projects = this.projectService.getAllProjects();

        return projects.stream()
                .map(RestProject::from)
                .collect(Collectors.toSet());
    }

    @GetMapping("/{name}")
    public RestProject get(@PathVariable String name) {
        Project project = this.projectService.getProjectByName(name);

        return RestProject.from(project);
    }

    @PostMapping
    public void saveProject(@RequestBody RestProjectDescription restProject) {
        Project project = this.pomHttpRetriever.getFromURL(restProject.pomURL(), restProject.name());
        this.projectService.save(project);
    }

    @DeleteMapping("/{projectName}")
    public void deleteProject(@PathVariable String projectName) {
        this.projectService.deleteByName(projectName);
    }
}
