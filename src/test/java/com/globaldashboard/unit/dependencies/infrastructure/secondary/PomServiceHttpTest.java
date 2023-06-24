package com.globaldashboard.unit.dependencies.infrastructure.secondary;

import com.globaldashboard.dependencies.infrastructure.primary.exception.InvalidPomException;
import com.globaldashboard.dependencies.infrastructure.secondary.PomServiceHttp;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class PomServiceHttpTest {

    @Test
    void shouldIndicateErrorWhenInvalidPomURL() {
        PomServiceHttp pomServiceHttp = new PomServiceHttp();

        assertThatExceptionOfType(InvalidPomException.class)
                .isThrownBy(() -> pomServiceHttp.getFromURL("invalidURL", "name"));
    }

}