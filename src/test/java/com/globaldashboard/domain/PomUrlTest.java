package com.globaldashboard.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class PomUrlTest {

    @Test
    void shouldBuildValidPomUrl() {
        assertThatNoException()
                .isThrownBy(() -> new PomUrl("http://repo-url/pom.xml"));
    }
    @ParameterizedTest
    @MethodSource("invalidPomUrlProvider")
    void shouldNotBuildInvalidPomUrl(String url) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PomUrl(url));
    }

    public static Stream<Arguments> invalidPomUrlProvider() {
        return Stream.of(
                Arguments.of("not-valid-pom.xml"),
                Arguments.of("http://not-a-pom")
        );
    }
}
