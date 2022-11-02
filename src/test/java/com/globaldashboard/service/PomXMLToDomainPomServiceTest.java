package com.globaldashboard.service;

import com.globaldashboard.domain.Pom;
import com.globaldashboard.domain.service.DomainPomService;
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
public class PomXMLToDomainPomServiceTest {

    private Document pomXML;

    private DomainPomService pomXMLToDomainPomService;

    @BeforeAll
    void setUp() throws IOException, ParserConfigurationException, SAXException {
        pomXML = getPom();
        pomXMLToDomainPomService = new DomainPomService();
    }

    @Test
    void shouldExtractProjectNameFromXML() {
        Pom pom = pomXMLToDomainPomService.parseXMLPOM(pomXML);

        assertThat(pom.projectName()).isEqualTo("aperotech");
    }

    @Test
    void shouldExtractProjectVersionFromXML()  {
        Pom pom = pomXMLToDomainPomService.parseXMLPOM(pomXML);

        assertThat(pom.projectVersion()).isEqualTo("0.0.1-SNAPSHOT");
    }

    @Test
    void shouldExtractDescriptionFromXML()  {
        Pom pom = pomXMLToDomainPomService.parseXMLPOM(pomXML);

        assertThat(pom.description()).isEqualTo("Demo project for Apero Tech");
    }

    @Test
    void shouldExtractJavaVersionFromXML()  {
        Pom pom = pomXMLToDomainPomService.parseXMLPOM(pomXML);

        assertThat(pom.java()).isEqualTo("17");
    }

    @Test
    void shouldExtractDependenciesFromXML()  {
        Pom pom = pomXMLToDomainPomService.parseXMLPOM(pomXML);

        assertThat(pom.dependencies()).hasSize(18);
    }

    private Document getPom() throws SAXException, IOException, ParserConfigurationException {
        File pomFile = new File("src/test/java/com/aperotech/kata/fixture/pom.xml");
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(pomFile);
    }
}
