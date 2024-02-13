package com.globaldashboard.dependencies.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PercentRateTest {

    @Test
    void shouldGetValueForValidRate() {
        PercentRate rate = new PercentRate(12);

        assertThat(rate.value()).isEqualTo(12);
    }

    @Test
    void shouldBePositive() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PercentRate(-1));
    }

    @Test
    void shouldBeUnderHundred() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PercentRate(101));
    }

    @Test
    void shouldBeBuildableFromNumeratorAndDenominator() {
        PercentRate percentRate = PercentRate.buildFrom(5, 10);

        assertThat(percentRate.value()).isEqualTo(50);
    }

    @Test
    void shouldBeBuildableFromLongNumeratorAndLongDenominator() {
        PercentRate percentRate = PercentRate.buildFrom(5L, 10L);

        assertThat(percentRate.value()).isEqualTo(50);
    }
}
