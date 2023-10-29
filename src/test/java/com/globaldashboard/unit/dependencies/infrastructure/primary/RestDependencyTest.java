package com.globaldashboard.unit.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.GroupId;
import com.globaldashboard.dependencies.infrastructure.primary.RestDependency;
import com.globaldashboard.domain.ArtifactId;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RestDependencyTest {

    @Test
    void shouldBeBuildableFromDomain() {
        Dependency dependency = new Dependency("groupId", "artifact-id", "1.2.3-SNAPSHOT", List.of("CVE-2023-35116"));
        RestDependency expectedRestDependency = new RestDependency("groupId", "artifact-id", "1.2.3-SNAPSHOT", List.of("CVE-2023-35116"));

        RestDependency restDependency = RestDependency.from(dependency);

        assertThat(restDependency).isEqualTo(expectedRestDependency);
    }

    @Test
    void shouldBeBuildableFromDomainWithNoVersion() {
        Dependency dependency = new Dependency(new GroupId("groupId"), new ArtifactId("artifact-id"), Optional.empty(), Optional.empty());
        RestDependency expectedRestDependency = new RestDependency("groupId", "artifact-id", "", List.of());

        RestDependency restDependency = RestDependency.from(dependency);

        assertThat(restDependency).isEqualTo(expectedRestDependency);
    }
}