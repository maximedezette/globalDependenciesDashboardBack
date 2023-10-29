package com.globaldashboard.unit.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.infrastructure.primary.RestDependency;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RestDependencyTest {

    @Test
    void shouldBeBuildableFromDomain() {
        Dependency dependency = Dependency.builder()
                .withGroupId("groupId")
                .withArtifactId("artifact-id")
                .withVersion("1.2.3-SNAPSHOT")
                .withCVEList(List.of("CVE-2023-35116"))
                .build();
        RestDependency expectedRestDependency = new RestDependency("groupId", "artifact-id", "1.2.3-SNAPSHOT", List.of("CVE-2023-35116"));

        RestDependency restDependency = RestDependency.from(dependency);

        assertThat(restDependency).isEqualTo(expectedRestDependency);
    }

    @Test
    void shouldBeBuildableFromDomainWithNoVersion() {
        Dependency dependency = Dependency.builder()
                .withGroupId("groupId")
                .withArtifactId("artifact-id")
                .build();
        RestDependency expectedRestDependency = new RestDependency("groupId", "artifact-id", "", List.of());

        RestDependency restDependency = RestDependency.from(dependency);

        assertThat(restDependency).isEqualTo(expectedRestDependency);
    }
}