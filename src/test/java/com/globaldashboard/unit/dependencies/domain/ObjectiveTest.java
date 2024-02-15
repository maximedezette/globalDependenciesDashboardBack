package com.globaldashboard.unit.dependencies.domain;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.Objective;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectiveTest {

    @Test
    void shouldBeAchievedWhenDependencyAtSameVersion() {
        Objective objective = getLog4jObjective();
        Dependency dependency = getLog4jDependency();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isTrue();
    }

    @Test
    void shouldBeAchievedWhenDependencyAtHigherVersion() {
        Objective objective = getLog4jObjective();
        Dependency dependency = getLog4jDependency();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isTrue();
    }

    @Test
    void shouldNotBeAchievedWhenArtifactIdDoesntMatch() {
        Objective objective = getLog4jObjective();
        Dependency dependency = getLog4jBuilder()
                .withArtifactId("different-artifact-id")
                .build();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    @Test
    void shouldNotBeAchievedWhenGroupIdDoesntMatch() {
        Objective objective = getLog4jObjective();
        Dependency dependency = getLog4jBuilder()
                .withGroupId("differentGroupId")
                .build();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    @Test
    void shouldNotBeAchievedWhenDependencyAtLowerVersion() {
        Dependency dependency = getLog4jBuilder()
                .withVersion("0.0.0")
                .build();
        Objective objective = getLog4jObjective();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    private Objective getLog4jObjective() {
        return new Objective("org.apache.logging.log4j", "log4j", "2.20.0");
    }

    private Dependency getLog4jDependency() {
        return getLog4jBuilder().build();
    }

    private Dependency.Builder getLog4jBuilder() {
        return Dependency.builder()
                .withGroupId("org.apache.logging.log4j")
                .withArtifactId("log4j")
                .withVersion("2.20.0");
    }
}
