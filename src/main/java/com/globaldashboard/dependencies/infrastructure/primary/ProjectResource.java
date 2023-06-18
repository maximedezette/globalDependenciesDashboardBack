package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.application.ProjectService;
import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.domain.ProjectDescription;
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
        Set<String> pomURLs = this.projectService.getAllProjects().stream()
                .map(ProjectDescription::pomURL)
                .collect(Collectors.toSet());

        Set<Project> poms = this.pomHttpRetriever.getFromURLs(pomURLs);

        return poms.stream()
                .map(RestProject::from)
                .collect(Collectors.toSet());
    }

    @GetMapping("/{name}")
    public RestProject get(@PathVariable String name) {
        Project pom = this.projectService.getProjectByName(name);

        return RestProject.from(pom);
    }

    @PostMapping
    public void saveProject(@RequestBody RestProjectDescription restProject) {
        ProjectDescription projectDescription = restProject.toDomain();
        Project project = this.pomHttpRetriever.getFromURL(projectDescription.pomURL());
        this.projectService.save(project, projectDescription);
    }

    @DeleteMapping("/{projectName}")
    public void deleteProject(@PathVariable String projectName) {
        this.projectService.deleteByName(projectName);
    }
}
