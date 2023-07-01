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
        Dependency dependency = new Dependency("groupId", "different-artifact-id", "2.0.0");

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    @Test
    void shouldNotBeAchievedWhenGroupIdDoesntMatch() {
        Objective objective = getObjective();
        Dependency dependency = new Dependency("differentGroupId", "artifact-id", "2.0.0");

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    @Test
    void shouldNotBeAchievedWhenDependencyAtLowerVersion() {
        Dependency dependency = new Dependency("groupId", "artifact-id", "0.0.0");
        Objective objective = getObjective();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    private Objective getObjective() {
        return new Objective("groupId", "artifact-id", "1.0.0");
    }

    private Dependency getDependency() {
        return new Dependency("groupId", "artifact-id", "2.0.0");
    }
}
