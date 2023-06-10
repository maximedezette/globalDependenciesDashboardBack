package com.globaldashboard.application;

import com.globaldashboard.dependencies.application.PomService;
import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PomServiceTest {

    private Document pomXML;

    private PomService pomXMLToDomainPomService;

    @BeforeAll
    void setUp() throws IOException, ParserConfigurationException, SAXException {
        pomXML = getPom();
        pomXMLToDomainPomService = new PomService();
    }

    @Test
    void shouldExtractProjectNameFromXML() {
        Project pom = pomXMLToDomainPomService.parseXMLPOM(pomXML);

        assertThat(pom.projectName()).isEqualTo("aperotech");
    }

    @Test
    void shouldExtractProjectVersionFromXML()  {
        Project pom = pomXMLToDomainPomService.parseXMLPOM(pomXML);
        SemanticVersion expectedSemanticVersion = SemanticVersion.from("0.0.1-SNAPSHOT");

        SemanticVersion version = pom.projectVersion();

        assertThat(version).isEqualTo(expectedSemanticVersion);
    }

    @Test
    void shouldExtractDescriptionFromXML()  {
        Project pom = pomXMLToDomainPomService.parseXMLPOM(pomXML);

        assertThat(pom.description()).isEqualTo("Demo project for Apero Tech");
    }

    @Test
    void shouldExtractJavaVersionFromXML()  {
        Project pom = pomXMLToDomainPomService.parseXMLPOM(pomXML);

        assertThat(pom.java()).isEqualTo("17");
    }

    @Test
    void shouldExtractDependenciesFromXML()  {
        Project pom = pomXMLToDomainPomService.parseXMLPOM(pomXML);

        assertThat(pom.dependencies()).hasSize(18);
    }

    private Document getPom() throws SAXException, IOException, ParserConfigurationException {
        File pomFile = new File("src/test/java/com/globaldashboard/fixture/pom.xml");
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(pomFile);
    }
}
