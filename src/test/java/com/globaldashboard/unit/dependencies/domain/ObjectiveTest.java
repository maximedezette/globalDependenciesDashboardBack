package com.globaldashboard.unit.dependencies.domain;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.Objective;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectiveTest {

    @Test
    void shouldBeAchievedWhenDependencyAtSameVersion() {
        Objective objective = getLog4jObjective();
        Dependency dependency = getAchievingDependencyBuilder(objective)
                .build();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isTrue();
    }

    @Test
    void shouldBeAchievedWhenDependencyAtHigherVersion() {
        Objective objective = getLog4jObjective();
        Dependency dependency = getAchievingDependencyBuilder(objective)
                .build();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isTrue();
    }

    @Test
    void shouldNotBeAchievedWhenArtifactIdDoesntMatch() {
        Objective objective = getLog4jObjective();
        Dependency dependency = getAchievingDependencyBuilder(objective)
                .withArtifactId("different-artifact-id")
                .build();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    @Test
    void shouldNotBeAchievedWhenGroupIdDoesntMatch() {
        Objective objective = getLog4jObjective();
        Dependency dependency = getAchievingDependencyBuilder(objective)
                .withGroupId("differentGroupId")
                .build();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    @Test
    void shouldNotBeAchievedWhenDependencyAtLowerVersion() {
        Objective objective = getLog4jObjective();
        Dependency dependency = getAchievingDependencyBuilder(objective)
                .withVersion("0.0.0")
                .build();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    private Objective getLog4jObjective() {
        return new Objective("org.apache.logging.log4j", "log4j", "2.20.0");
    }

    private Dependency.Builder getAchievingDependencyBuilder(Objective objective) {
        return Dependency.builder()
                .withGroupId(objective.groupId().label())
                .withArtifactId(objective.artifactId().name())
                .withVersion(objective.version().readableValue());
    }
}
