package com.globaldashboard.infrastructure.primary;

import com.globaldashboard.application.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    Collection<RestProject> getAllProjects() {
            return this.projectService.getAllProjects()
                    .stream()
                    .map(RestProject::from)
                    .collect(Collectors.toSet());
    }
}
