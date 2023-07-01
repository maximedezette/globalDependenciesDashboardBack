package com.globaldashboard.unit.dependencies.domain;

import com.globaldashboard.domain.ArtifactId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ArtifactIdTest {

    @Test
    void shouldNotBuildArtifactIdWithUppercase() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ArtifactId("LOG4J"));
    }

    @Test
    void shouldNotBuildArtifactIfWithNoName() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ArtifactId(""));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ArtifactId(null));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ArtifactId("    "));
    }
    @Test
    void shouldBuildValidArtifactId() {
        ArtifactId artifactId = new ArtifactId("artifact-id");

        assertThat(artifactId.name()).isEqualTo("artifact-id");
    }
}
