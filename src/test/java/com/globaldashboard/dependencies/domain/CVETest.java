package com.globaldashboard.dependencies.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CVETest {

    @Test
    void shouldBuildValidCVE() {
        String identifier = "CVE-2023-35116";

        CVE cve = new CVE(identifier);

        assertThat(cve.identifier()).isEqualTo(identifier);
    }

    @Test
    void shouldNotBuildInvalidCVE() {
        String identifier = "CVE2023";

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new CVE(identifier));
    }

}