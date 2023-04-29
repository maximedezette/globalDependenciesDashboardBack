package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.application.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("projects")
public class ProjectResource {

    private final ProjectService projectService;

    @Autowired
    public ProjectResource(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public Collection<RestProject> getAllProjects() {
        return this.projectService.getAllProjects()
                .stream()
                .map(RestProject::from)
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

    @PostMapping
    public void saveProject(@RequestBody RestProject restProject) {
        this.projectService.save(restProject.toDomain());
    }

    @DeleteMapping("/{projectName}")
    public void deleteProject(@PathVariable String projectName) {
        this.projectService.deleteByName(projectName);
    }
}
