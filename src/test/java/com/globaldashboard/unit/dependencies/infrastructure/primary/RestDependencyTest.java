package com.globaldashboard.unit.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.GroupId;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import com.globaldashboard.dependencies.infrastructure.primary.RestDependency;
import com.globaldashboard.fixture.GroupIdFixture;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RestDependencyTest {

    @Test
    void shouldBeBuildableFromDomain() {
        Dependency dependency = new Dependency(GroupIdFixture.get(), "artifactId", Optional.of(SemanticVersion.from("1.2.3-SNAPSHOT")));
        RestDependency expectedRestDependency = new RestDependency("groupId", "artifactId", "1.2.3-SNAPSHOT");

        RestDependency restDependency = RestDependency.from(dependency);

        assertThat(restDependency).isEqualTo(expectedRestDependency);
    }
}