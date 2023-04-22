package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Objective;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RestObjectiveTest {

    @Test
    void shouldBeConvertableToDomain() {
        RestObjective restObjective = new RestObjective("org.springframework.boot", "spring-boot-starter-parent", "2.6.0");

        Objective objective = restObjective.toDomain();

        assertThat(objective.groupId()).isEqualTo("org.springframework.boot");
        assertThat(objective.artifactId()).isEqualTo("spring-boot-starter-parent");
        assertThat(objective.version()).isEqualTo(SemanticVersion.from("2.6.0"));
    }

}