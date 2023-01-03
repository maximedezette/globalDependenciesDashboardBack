package com.globaldashboard.infrastructure.secondary;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.Pom;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import com.globaldashboard.dependencies.infrastructure.secondary.PomFactory;
import com.globaldashboard.fixture.DocumentPomFixtures;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PomEntityFactoryTest {

    private PomFactory pomFactory;
    private Document pomXML;
    
    @BeforeAll
    void setUp() throws ParserConfigurationException, IOException, SAXException {
        this.pomFactory = new PomFactory();
        this.pomXML = DocumentPomFixtures.getDocument();
    }

    @Test
    void shouldExtractProjectNameFromXML() {
        Pom pom = pomFactory.getPomFrom(pomXML);

        assertThat(pom.projectName()).isEqualTo("aperotech");
    }

    @Test
    void shouldExtractProjectVersionFromXML() {
        Pom pom = pomFactory.getPomFrom(pomXML);
        SemanticVersion expectedVersion = SemanticVersion.from("0.0.1-SNAPSHOT");

        SemanticVersion version = pom.projectVersion();

        assertThat(version).isEqualTo(expectedVersion);
    }

    @Test
    void shouldExtractDescriptionFromXML() {
        Pom pom = pomFactory.getPomFrom(pomXML);

        assertThat(pom.description()).isEqualTo("Demo project for Apero Tech");
    }

    @Test
    void shouldExtractJavaVersionFromXML() {
        Pom pom = pomFactory.getPomFrom(pomXML);

        assertThat(pom.java()).isEqualTo("17");
    }

    @Test
    void shouldExtractDependenciesFromXML() {
        Pom pom = pomFactory.getPomFrom(pomXML);

        assertThat(pom.dependencies()).hasSize(18);
    }

    @Test
    void shouldReplaceVariableVersionInDependencies() {
        Pom pom = pomFactory.getPomFrom(pomXML);

        Dependency dependency = new Dependency("aperotech", "org.junit", "junit-bom", "5.9.0");

        assertThat(pom.dependencies()).contains(dependency);
    }

}