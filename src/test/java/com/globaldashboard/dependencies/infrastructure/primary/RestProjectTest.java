package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.GroupId;
import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RestProjectTest {

    @Test
    void shouldConvertFromDomainProject() {
        Project project = new Project(SemanticVersion.from("1.2.3-SNAPSHOT"), "name", "description", "17", getDependencies(), "https://raw.githubusercontent.com/maximedezette/globalDependenciesDashboardBack/main/pom.xml");

        RestProject restProject = RestProject.from(project);

        assertThat(restProject).isNotNull();
        assertThat(restProject.version()).isEqualTo("1.2.3-SNAPSHOT");
        assertThat(restProject.name()).isEqualTo("name");
        assertThat(restProject.description()).isEqualTo("description");
        assertThat(restProject.java()).isEqualTo("17");
        assertThat(restProject.pomURL()).isEqualTo("https://raw.githubusercontent.com/maximedezette/globalDependenciesDashboardBack/main/pom.xml");
        assertThat(restProject.dependencies()).containsExactlyElementsOf(getRestDependencies());
    }

    private List<RestDependency> getRestDependencies() {
        return List.of(new RestDependency("goupId", "artifactId", "1.0.0"));
    }

    private List<Dependency> getDependencies() {
        return List.of(new Dependency(new GroupId("goupId"), "artifactId", Optional.of(SemanticVersion.from("1.0.0"))));
    }
}