package com.globaldashboard.unit.dependencies.domain;

import com.globaldashboard.dependencies.domain.GroupId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class GroupIdTest {

    @ParameterizedTest
    @MethodSource("validGroupIdsProvider")
    void shouldBuildValidGroupId(String label) {
        GroupId groupId = new GroupId(label);

        assertThat(groupId.label()).isEqualTo(label);
    }
    @Test
    void shouldNotBuildGroupIdStartingWithADigit() {
       assertThatExceptionOfType(IllegalArgumentException.class)
               .isThrownBy(() -> new GroupId("1io.cucumber"));
    }

    @ParameterizedTest
    @MethodSource("groupIdsWithSpecialCharsProvider")
    void shouldNotBuildGroupIdWithSpecialChars(String label) {
       assertThatExceptionOfType(IllegalArgumentException.class)
               .isThrownBy(() -> new GroupId(label));
    }

    @Test
    void shouldNotBuildGroupIdWithNoValue() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new GroupId(""));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new GroupId("    "));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new GroupId(null));

    }

    public static Stream<Arguments> validGroupIdsProvider() {
        return Stream.of(
                Arguments.of("io.cucumber"),
                Arguments.of("io-cucumber"),
                Arguments.of("io_cucumber")
        );
    }
    public static Stream<Arguments> groupIdsWithSpecialCharsProvider() {
        return Stream.of(
                Arguments.of("io cucumber"),
                Arguments.of("io.cucumber$"),
                Arguments.of("io.c'ucumber"),
                Arguments.of(";io.cucumber")
        );
    }
}
