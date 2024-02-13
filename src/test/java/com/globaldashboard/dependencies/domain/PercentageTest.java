package com.globaldashboard.dependencies.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PercentageTest {

    @Test
    void shouldGetValueForValidRate() {
        Percentage rate = new Percentage(12);

        assertThat(rate.value()).isEqualTo(12);
    }

    @Test
    void shouldBePositive() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Percentage(-1));
    }

    @Test
    void shouldBeUnderHundred() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Percentage(101));
    }

    @Test
    void shouldBeBuildableFromNumeratorAndDenominator() {
        Percentage percentRate = Percentage.buildFrom(5, 10);

        assertThat(percentRate.value()).isEqualTo(50);
    }

    @Test
    void shouldBeBuildableFromLongNumeratorAndLongDenominator() {
        Percentage percentRate = Percentage.buildFrom(5L, 10L);

        assertThat(percentRate.value()).isEqualTo(50);
    }
}
