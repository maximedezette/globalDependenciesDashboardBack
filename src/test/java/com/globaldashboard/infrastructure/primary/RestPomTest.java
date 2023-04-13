package com.globaldashboard.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.ProjectInformation;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import com.globaldashboard.dependencies.infrastructure.primary.RestDependency;
import com.globaldashboard.dependencies.infrastructure.primary.RestProjectInformation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RestPomTest {

    @Test
    void shouldConvertFromDomainProject() {
        ProjectInformation pom = new ProjectInformation(SemanticVersion.from("1.2.3-SNAPSHOT"), "name", "description", "17", getDependencies());

        RestProjectInformation restPom = RestProjectInformation.from(pom);

        assertThat(restPom).isNotNull();
        assertThat(restPom.version()).isEqualTo("1.2.3-SNAPSHOT");
        assertThat(restPom.projectName()).isEqualTo("name");
        assertThat(restPom.description()).isEqualTo("description");
        assertThat(restPom.java()).isEqualTo("17");
        assertThat(restPom.dependencies()).containsExactlyElementsOf(getRestDependencies());
    }

    private List<RestDependency> getRestDependencies() {
        return List.of(new RestDependency("goupId", "artifactId", "1.0.0"));
    }

    private List<Dependency> getDependencies() {
        return List.of(new Dependency("goupId", "artifactId", Optional.of(SemanticVersion.from("1.0.0"))));
    }
}