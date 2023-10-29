package com.globaldashboard.fixture;

import com.globaldashboard.dependencies.domain.Dependency;

public class DependencyFixture {

    public static Dependency getCucumber() {
        return Dependency.builder()
                .withGroupId("io.cucumber")
                .withArtifactId("cucumber-bom")
                .withVersion("7.6.0")
                .build();
    }

    public static Dependency getJunit() {
        return Dependency.builder()
                .withGroupId("org.junit")
                .withArtifactId("junit-bom")
                .withVersion("5.9.0")
                .build();
    }
}
