package com.globaldashboard.domain;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.Objective;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectiveTest {

    @Test
    void shouldBeAchievedWhenDependencyAtSameVersion() {
        Objective objective = getObjective();
        Dependency dependency = new Dependency("groupid", "artifactId", Optional.of(SemanticVersion.from("1.0.0")));

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isTrue();
    }

    @Test
    void shouldBeAchievedWhenDependencyAtHigherVersion() {
        Objective objective = getObjective();
        Dependency dependency = new Dependency("groupid", "artifactId", Optional.of(SemanticVersion.from("2.0.0")));

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isTrue();
    }

    @Test
    void shouldNotBeAchievedWhenDependencyAtLowerVersion() {
        Dependency dependency = new Dependency("groupid", "artifactId", Optional.of(SemanticVersion.from("0.0.0")));
        Objective objective = getObjective();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    private Objective getObjective() {
        Dependency dependency = new Dependency("groupid", "artifactId", Optional.of(SemanticVersion.from("1.0.0")));
        return new Objective(dependency, SemanticVersion.from("1.0.0"));
    }
}
