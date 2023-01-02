package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.application.ProjectService;
import com.globaldashboard.dependencies.domain.Pom;
import com.globaldashboard.dependencies.domain.Project;
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
public class PomResource {


    private final ProjectService projectService;
    private final PomHttpRetriever pomHttpRetriever;

    @Autowired
    public PomResource(ProjectService projectService, PomHttpRetriever pomHttpRetriever) {
        this.projectService = projectService;
        this.pomHttpRetriever = pomHttpRetriever;
    }

    @GetMapping
    public Set<RestPom> getAll() {
        Set<String> pomURLs = this.projectService.getAllProjects().stream()
                .map(Project::pomURL)
                .collect(Collectors.toSet());

        Set<Pom> poms = this.pomHttpRetriever.getFromURLs(pomURLs);

        return poms.stream()
                .map(RestPom::from)
                .collect(Collectors.toSet());
    }

    @GetMapping("/project/{name}")
    public RestPom get(@PathVariable String name) {
        String pomURL = this.projectService.getProjectByName(name).pomURL();
        Pom pom = this.pomHttpRetriever.getFromURL(pomURL);

        return RestPom.from(pom);
    }


}

