package com.globaldashboard.unit.dependencies.domain;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.Objective;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectiveTest {

    @Test
    void shouldBeAchievedWhenDependencyAtSameVersion() {
        Objective objective = getObjective();
        Dependency dependency = getDependency();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isTrue();
    }

    @Test
    void shouldBeAchievedWhenDependencyAtHigherVersion() {
        Objective objective = getObjective();
        Dependency dependency = getDependency();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isTrue();
    }

    @Test
    void shouldNotBeAchievedWhenArtifactIdDoesntMatch() {
        Objective objective = getObjective();
        Dependency dependency = getDefaultBuilder()
                .withArtifactId("different-artifact-id")
                .build();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    @Test
    void shouldNotBeAchievedWhenGroupIdDoesntMatch() {
        Objective objective = getObjective();
        Dependency dependency = getDefaultBuilder()
                .withGroupId("differentGroupId")
                .build();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    @Test
    void shouldNotBeAchievedWhenDependencyAtLowerVersion() {
        Dependency dependency = getDefaultBuilder()
                .withVersion("0.0.0")
                .build();
        Objective objective = getObjective();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    private Objective getObjective() {
        return new Objective("org.apache.logging.log4j", "log4j", "2.20.0");
    }

    private Dependency getDependency() {
        return getDefaultBuilder().build();
    }

    private Dependency.Builder getDefaultBuilder() {
        return Dependency.builder()
                .withGroupId("org.apache.logging.log4j")
                .withArtifactId("log4j")
                .withVersion("2.20.0");
    }
}
