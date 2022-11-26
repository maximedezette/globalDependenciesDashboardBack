package com.globaldashboard.infrastructure.primary;

import com.globaldashboard.domain.Dependency;
import com.globaldashboard.domain.Pom;
import com.globaldashboard.domain.SemanticVersion;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RestPomTest {

    @Test
    void shouldConvertFromDomainProject() {
        Pom pom = new Pom(SemanticVersion.from("1.2.3-SNAPSHOT"), "name", "description", "17", getDependencies());

        RestPom restPom = RestPom.from(pom);

        assertThat(restPom).isNotNull();
        assertThat(restPom.version()).isEqualTo("1.2.3-SNAPSHOT");
        assertThat(restPom.projectName()).isEqualTo("name");
        assertThat(restPom.description()).isEqualTo("description");
        assertThat(restPom.java()).isEqualTo("17");
        assertThat(restPom.dependencies()).containsExactlyElementsOf(getDependencies());
    }

    private List<Dependency> getDependencies() {
        return List.of(new Dependency("dependencyProject", "goupId", "artifactId", "1.0.0"));
    }
}