package com.globaldashboard.infrastructure.primary;

import com.globaldashboard.application.ProjectService;
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

    @PostMapping
    public void saveProject(@RequestBody RestProject restProject) {
        this.projectService.save(restProject.toDomain());
    }

    @DeleteMapping("/{projectName}")
    public void deleteProject(@PathVariable String projectName) {
        this.projectService.deleteByName(projectName);
    }
}
