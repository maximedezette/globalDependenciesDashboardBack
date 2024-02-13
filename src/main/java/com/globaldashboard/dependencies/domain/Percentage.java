package com.globaldashboard.dependencies.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public record Percentage(int value) {
    public Percentage {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("A valid rate must be between 0 and 100");
        }
    }

    public static Percentage buildFrom(int numerator, int denominator) {
        int intValue = BigDecimal.valueOf(numerator)
                .divide(BigDecimal.valueOf(denominator), new MathContext(2, RoundingMode.HALF_UP))
                .multiply(BigDecimal.valueOf(100))
                .intValue();
        return new Percentage(intValue);
    }

    public static Percentage buildFrom(long numerator, long denominator) {
        return buildFrom((int) numerator, (int) denominator);
    }
}
