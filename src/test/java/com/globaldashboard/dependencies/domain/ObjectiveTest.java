package com.globaldashboard.dependencies.domain;

import com.globaldashboard.fixture.GroupIdFixture;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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
        Dependency dependency = new Dependency(GroupIdFixture.get(), "differentArtifactId", Optional.of(SemanticVersion.from("2.0.0")));

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    @Test
    void shouldNotBeAchievedWhenGroupIdDoesntMatch() {
        Objective objective = getObjective();
        Dependency dependency = new Dependency(new GroupId("differentGroupId"), "artifactId", Optional.of(SemanticVersion.from("2.0.0")));

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    @Test
    void shouldNotBeAchievedWhenDependencyAtLowerVersion() {
        Dependency dependency = new Dependency(GroupIdFixture.get(), "artifactId", Optional.of(SemanticVersion.from("0.0.0")));
        Objective objective = getObjective();

        boolean achieved = objective.isAchievedBy(dependency);

        assertThat(achieved).isFalse();
    }

    private Objective getObjective() {
        return new Objective(GroupIdFixture.get(), "artifactId", SemanticVersion.from("1.0.0"));
    }

    private Dependency getDependency() {
        return new Dependency(GroupIdFixture.get(), "artifactId", Optional.of(SemanticVersion.from("2.0.0")));
    }
}
