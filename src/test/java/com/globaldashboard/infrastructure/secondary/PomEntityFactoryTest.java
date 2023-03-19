package com.globaldashboard.infrastructure.secondary;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.ProjectInformation;
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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PomEntityFactoryTest {

    private PomFactory pomFactory;
    private Document pomXML;
    private Document childPomXML;

    @BeforeAll
    void setUp() throws ParserConfigurationException, IOException, SAXException {
        this.pomFactory = new PomFactory();
        this.pomXML = DocumentPomFixtures.getPom();
        this.childPomXML = DocumentPomFixtures.getChildPom();
    }

    @Test
    void shouldExtractProjectNameFromXML() {
        ProjectInformation pom = pomFactory.getPomFrom(Map.of("", pomXML));

        assertThat(pom.projectName()).isEqualTo("aperotech");
    }

    @Test
    void shouldExtractProjectVersionFromXML() {
        ProjectInformation pom = pomFactory.getPomFrom(Map.of("", pomXML));
        SemanticVersion expectedVersion = SemanticVersion.from("0.0.1-SNAPSHOT");

        SemanticVersion version = pom.projectVersion();

        assertThat(version).isEqualTo(expectedVersion);
    }

    @Test
    void shouldExtractDescriptionFromXML() {
        ProjectInformation pom = pomFactory.getPomFrom(Map.of("", pomXML));

        assertThat(pom.description()).isEqualTo("Demo project for Apero Tech");
    }

    @Test
    void shouldExtractJavaVersionFromXML() {
        ProjectInformation pom = pomFactory.getPomFrom(Map.of("", pomXML));

        assertThat(pom.java()).isEqualTo("17");
    }

    @Test
    void shouldExtractDependenciesFromXML() {
        ProjectInformation pom = pomFactory.getPomFrom(Map.of("", pomXML));

        assertThat(pom.dependencies()).hasSize(18);
    }

    @Test
    void shouldReplaceVariableVersionInDependencies() {
        ProjectInformation pom = pomFactory.getPomFrom(Map.of("", pomXML));
        Dependency dependency = new Dependency("org.junit", "junit-bom", "5.9.0");

        assertThat(pom.dependencies()).contains(dependency);
    }

    @Test
    void shouldGetDependenciesFromMultiplePom() {
        ProjectInformation pom = pomFactory.getPomFrom(
                Map.of(
                        "com.global-dependenceies-dashboard-back", pomXML,
                        "", childPomXML
                ));

        assertThat(pom.dependencies()).hasSize(19);
    }

    @Test
    void shouldGetPropertiesFromParentPom() {
        ProjectInformation pom = pomFactory.getPomFrom(Map.of(
                "com.global-dependenceies-dashboard-back", pomXML,
                "", childPomXML
        ));
        Dependency expectedDependency = new Dependency("org.junit", "junit-bom", "5.9.0");

        assertThat(pom.dependencies()).contains(expectedDependency);
    }

}