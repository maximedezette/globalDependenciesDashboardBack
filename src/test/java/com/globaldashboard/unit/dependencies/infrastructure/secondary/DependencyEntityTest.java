package com.globaldashboard.unit.dependencies.infrastructure.secondary;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.infrastructure.secondary.CVEEntity;
import com.globaldashboard.dependencies.infrastructure.secondary.DependencyEntity;
import com.globaldashboard.fixture.CVEFixture;
import com.globaldashboard.fixture.DependencyFixture;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class DependencyEntityTest {

    @Test
    void shouldBeBuildableFromDomain() {
        Dependency dependency = DependencyFixture.getCucumber();
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
        Dependency dependency = Dependency.builder()
                .withGroupId("io.cucumber")
                .withArtifactId("cucumber-bom")
                .build();
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
        CVEEntity cveEntity = new CVEEntity();
        String identifier = CVEFixture.validIdentifier;
        cveEntity.setIdentifier(identifier);
        DependencyEntity dependencyEntity = new DependencyEntity();
        dependencyEntity.setGroupId("io.cucumber");
        dependencyEntity.setVersion("7.6.0");
        dependencyEntity.setArtifactId("cucumber-bom");
        dependencyEntity.addCVE(cveEntity);
        Dependency expected = Dependency
                .builder()
                .withGroupId("io.cucumber")
                .withArtifactId("cucumber-bom")
                .withVersion("7.6.0")
                .withCVEList(List.of(identifier))
                .build();

        Dependency actual = dependencyEntity.toDomain();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldBeConvertableToDomainWithDefaultVersion() {
        DependencyEntity dependencyEntity = new DependencyEntity();
        dependencyEntity.setGroupId("io.cucumber");
        dependencyEntity.setVersion("0.0.0");
        dependencyEntity.setArtifactId("cucumber-bom");
        Dependency expected = Dependency.builder()
                .withGroupId("io.cucumber")
                .withArtifactId("cucumber-bom")
                .build();

        Dependency actual = dependencyEntity.toDomain();

        assertThat(actual).isEqualTo(expected);
    }

}