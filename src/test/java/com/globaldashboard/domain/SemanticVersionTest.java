package com.globaldashboard.domain;

import com.globaldashboard.dependencies.domain.SemanticVersion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SemanticVersionTest {

    @Test
    void shouldBeBuildableFromSimpleStringVersion() {
        String version = "1.2.3";

        SemanticVersion semanticVersion = SemanticVersion.from(version);

        assertThat(semanticVersion.major()).isEqualTo(1);
        assertThat(semanticVersion.minor()).isEqualTo(2);
        assertThat(semanticVersion.patch()).isEqualTo(3);
    }

    @Test
    void shouldBeBuildableFromSimpleStringVersionWithLabel() {
        String version = "1.2.3-SNAPSHOT";

        SemanticVersion semanticVersion = SemanticVersion.from(version);

        assertThat(semanticVersion.major()).isEqualTo(1);
        assertThat(semanticVersion.minor()).isEqualTo(2);
        assertThat(semanticVersion.patch()).isEqualTo(3);
        assertThat(semanticVersion.label()).isEqualTo("SNAPSHOT");
    }

    @Test
    void shouldPrintReadableVersion() {
        SemanticVersion semanticVersion = SemanticVersion.from("1.2.3");

        String readableVersion = semanticVersion.toString();

        assertThat(readableVersion).isEqualTo("1.2.3");
    }

}