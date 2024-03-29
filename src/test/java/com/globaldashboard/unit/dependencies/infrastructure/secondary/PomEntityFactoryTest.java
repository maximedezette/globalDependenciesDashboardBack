package com.globaldashboard.unit.dependencies.infrastructure.secondary;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import com.globaldashboard.dependencies.infrastructure.secondary.PomFactory;
import com.globaldashboard.fixture.DependencyFixture;
import com.globaldashboard.fixture.DocumentPomFixtures;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PomEntityFactoryTest {

    private PomFactory pomFactory;
    private Document pomXML;
    private Document childPomXML;

    private Project project;

    @BeforeAll
    void setUp() throws ParserConfigurationException, IOException, SAXException {
        this.pomFactory = new PomFactory();
        this.pomXML = DocumentPomFixtures.getPom();
        this.childPomXML = DocumentPomFixtures.getChildPom();
        project = pomFactory.getPomFrom(Map.of("", pomXML), "", "");
    }

    @Test
    void shouldExtractProjectVersionFromXML() {
        SemanticVersion expectedVersion = SemanticVersion.from("0.0.1-SNAPSHOT");

        SemanticVersion version = project.projectVersion();

        assertThat(version).isEqualTo(expectedVersion);
    }

    @Test
    void shouldExtractDescriptionFromXML() {
        assertThat(project.description()).isEqualTo("Demo project for Apero Tech");
    }

    @Test
    void shouldExtractJavaVersionFromXML() {
        assertThat(project.java()).isEqualTo("17");
    }

    @Test
    void shouldExtractDependenciesFromXML() {
        assertThat(project.dependencies()).hasSize(18);
    }

    @Test
    void shouldReplaceVariableVersionInDependencies() {
        Dependency dependency = DependencyFixture.getJunit();

        assertThat(project.dependencies()).contains(dependency);
    }

    @Test
    void shouldGetDependenciesFromMultiplePom() {
        Project project = pomFactory.getPomFrom(
                Map.of(
                        "com.global-dependenceies-dashboard-back", pomXML,
                        "", childPomXML
                ), "", "");

        assertThat(project.dependencies()).hasSize(19);
    }

    @Test
    void shouldGetPropertiesFromParentPom() {
        Project pom = pomFactory.getPomFrom(Map.of(
                "com.global-dependenceies-dashboard-back", pomXML,
                "", childPomXML
        ), "", "");
        Dependency expectedDependency = DependencyFixture.getJunit();
        ;

        assertThat(pom.dependencies()).contains(expectedDependency);
    }

}