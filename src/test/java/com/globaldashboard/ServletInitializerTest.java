package com.globaldashboard;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ServletInitializerTest {

    private static String getSourceName(SpringApplicationBuilder application) {
        return application.build().getAllSources().stream().toList().get(0).toString();
    }

    @Test
    void shouldInitializeApplication() {
        SpringApplicationBuilder application = new SpringApplicationBuilder();
        ServletInitializer servletInitializer = new ServletInitializer();

        servletInitializer.configure(application);

        assertThat(application.build().getAllSources()).hasSize(1);
        assertThat(getSourceName(application)).isEqualTo("class com.globaldashboard.GlobalDependenciesDashboardApplication");
    }
}