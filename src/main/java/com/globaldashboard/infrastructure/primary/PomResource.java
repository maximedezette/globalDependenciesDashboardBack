package com.globaldashboard.infrastructure.primary;

import com.globaldashboard.application.ProjectService;
import com.globaldashboard.domain.Pom;
import com.globaldashboard.domain.port.secondary.PomHttpRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

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

    @GetMapping("/project/{name}")
    public Pom get(@PathVariable String name)  {
        String pomURL = this.projectService.getProjectByName(name).pomURL();

        return this.pomHttpRetriever.getFromURL(pomURL);
    }


}

