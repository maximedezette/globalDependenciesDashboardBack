package com.globaldashboard.dependencies.domain;

import com.globaldashboard.fixture.CVEFixture;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DependencyTest {

    @Test
    void shouldNotHaveCVEWhenNoneProvided() {
        Dependency dependency = Dependency.builder()
                .withCVEList(List.of())
                .build();

        Optional<List<CVE>> actual = dependency.CVEList();

        assertThat(actual).isEqualTo(Optional.empty());
    }

    @Test
    void shouldAddCVEWhenProvided() {
        Dependency dependency = Dependency.builder()
                .withCVEList(List.of(CVEFixture.validIdentifier))
                .build();
        Optional<List<CVE>> expected = Optional.of(List.of(CVEFixture.cve()));

        Optional<List<CVE>> actual = dependency.CVEList();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldNotHaveVersionWhenNoneProvided() {
        Dependency dependency = Dependency.builder()
                .withVersion("")
                .build();

        Optional<SemanticVersion> actual = dependency.version();

        assertThat(actual).isEqualTo(Optional.empty());
    }

    @Test
    void shouldAddVersionWhenProvided() {
        Dependency dependency = Dependency.builder()
                .withVersion("2.7.0")
                .build();
        Optional<SemanticVersion> expected = Optional.of(SemanticVersion.from("2.7.0"));

        Optional<SemanticVersion> actual = dependency.version();

        assertThat(actual).isEqualTo(expected);
    }

}