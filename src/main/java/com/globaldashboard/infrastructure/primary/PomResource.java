package com.globaldashboard.infrastructure.primary;

import com.globaldashboard.application.ProjectService;
import com.globaldashboard.domain.Pom;
import com.globaldashboard.domain.port.primary.DependenciesFromPom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("/pom")
public class PomResource {

    private final DependenciesFromPom pomXMLToDomainPomService;
    private final ProjectService projectService;

    @Autowired
    public PomResource(DependenciesFromPom dependenciesFromPomService, ProjectService projectService) {
        this.pomXMLToDomainPomService = dependenciesFromPomService;
        this.projectService = projectService;
    }

    @GetMapping("/project/{name}")
    public Pom get(@PathVariable String name) {

        String pomURL = this.projectService.getProjectByName(name).pomURL();
        URL url;
        try {
            url = new URL(pomURL);
            HttpURLConnection pomResponse;
            pomResponse = (HttpURLConnection) url.openConnection();
            pomResponse.setRequestMethod("GET");
            Document pomXML = getPomXML(pomResponse);
            pomResponse.disconnect();

            return this.pomXMLToDomainPomService.parseXMLPOM(pomXML);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private Document getPomXML(HttpURLConnection pomResponse) throws SAXException, IOException, ParserConfigurationException {
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(pomResponse.getInputStream());
    }
}

