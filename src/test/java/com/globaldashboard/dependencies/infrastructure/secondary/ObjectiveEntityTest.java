package com.globaldashboard.dependencies.infrastructure.secondary;

import com.globaldashboard.dependencies.domain.GroupId;
import com.globaldashboard.dependencies.domain.Objective;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectiveEntityTest {

    @Test
    void shouldBeBuildableFromDomain() {
        Objective objective = new Objective("org.springframework.boot", "spring-boot-starter-parent","2.6.1");
        ObjectiveEntity expectedObjectiveEntity = new ObjectiveEntity();
        expectedObjectiveEntity.setGroupId("org.springframework.boot");
        expectedObjectiveEntity.setArtifactId("spring-boot-starter-parent");
        expectedObjectiveEntity.setVersion("2.6.1");

        ObjectiveEntity objectiveEntity = ObjectiveEntity.from(objective);

        assertThat(objectiveEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedObjectiveEntity);
    }

    @Test
    void shouldBeConvertableToDomain() {
        ObjectiveEntity objectiveEntity = new ObjectiveEntity();
        objectiveEntity.setGroupId("org.springframework.boot");
        objectiveEntity.setArtifactId("spring-boot-starter-parent");
        objectiveEntity.setVersion("2.6.1");
        Objective expectedObjective = new Objective("org.springframework.boot", "spring-boot-starter-parent", "2.6.1");

        Objective objective = objectiveEntity.toDomain();

        assertThat(objective)
                .usingRecursiveComparison()
                .isEqualTo(expectedObjective);
    }

}