package com.globaldashboard.unit.dependencies.infrastructure.secondary;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.GroupId;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import com.globaldashboard.dependencies.infrastructure.secondary.DependencyEntity;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class DependencyEntityTest {

    @Test
    void shouldBeBuildableFromDomain() {
        Dependency dependency = new Dependency("io.cucumber", "cucumber-bom", "7.6.0");
        DependencyEntity expected = new DependencyEntity();
        expected.setVersion("7.6.0");
        expected.setGroupId("io.cucumber");
        expected.setArtifactId("cucumber-bom");

        DependencyEntity actual = DependencyEntity.from(dependency);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldBeBuildableFromDependencyWithNoVersion() {
        Dependency dependency = new Dependency("io.cucumber", "cucumber-bom");
        DependencyEntity expected = new DependencyEntity();
        expected.setGroupId("io.cucumber");
        expected.setVersion("0.0.0");
        expected.setArtifactId("cucumber-bom");

        DependencyEntity actual = DependencyEntity.from(dependency);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldBeConvertableToDomain() {
        DependencyEntity dependencyEntity = new DependencyEntity();
        dependencyEntity.setGroupId("io.cucumber");
        dependencyEntity.setVersion("7.6.0");
        dependencyEntity.setArtifactId("cucumber-bom");
        Dependency expected = new Dependency("io.cucumber", "cucumber-bom", "7.6.0");

        Dependency actual = dependencyEntity.toDomain();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldBeConvertableToDomainWithDefaultVersion() {
        DependencyEntity dependencyEntity = new DependencyEntity();
        dependencyEntity.setGroupId("io.cucumber");
        dependencyEntity.setVersion("0.0.0");
        dependencyEntity.setArtifactId("cucumber-bom");
        Dependency expected = new Dependency("io.cucumber", "cucumber-bom");

        Dependency actual = dependencyEntity.toDomain();

        assertThat(actual).isEqualTo(expected);
    }

}