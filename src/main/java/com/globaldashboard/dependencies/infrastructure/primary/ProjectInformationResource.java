package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.application.ProjectService;
import com.globaldashboard.dependencies.domain.ProjectInformation;
import com.globaldashboard.dependencies.domain.port.secondary.PomHttpRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pom")
public class ProjectInformationResource {


    private final ProjectService projectService;
    private final PomHttpRetriever pomHttpRetriever;

    @Autowired
    public ProjectInformationResource(ProjectService projectService, PomHttpRetriever pomHttpRetriever) {
        this.projectService = projectService;
        this.pomHttpRetriever = pomHttpRetriever;
    }

    @GetMapping
    public Set<RestProjectInformation> getAll() {
        Set<String> pomURLs = this.projectService.getAllProjects().stream()
                .map(project -> project.pomURL().url())
                .collect(Collectors.toSet());

        Set<ProjectInformation> poms = this.pomHttpRetriever.getFromURLs(pomURLs);

        return poms.stream()
                .map(RestProjectInformation::from)
                .collect(Collectors.toSet());
    }

    @GetMapping("/project/{name}")
    public RestProjectInformation get(@PathVariable String name) {
        String pomURL = this.projectService.getProjectByName(name).pomURL().url();
        ProjectInformation pom = this.pomHttpRetriever.getFromURL(pomURL);

        return RestProjectInformation.from(pom);
    }


}

