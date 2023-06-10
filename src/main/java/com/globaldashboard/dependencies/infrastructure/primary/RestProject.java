package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Project;

import java.util.Collection;
import java.util.List;

public record RestProject(String version, String projectName, String description, String java,
                          Collection<RestDependency> dependencies) {
    public static RestProject from(Project pom) {
        String version = pom.projectVersion().toString();
        List<RestDependency> restDependencies = pom.dependencies().stream()
                .map(RestDependency::from)
                .toList();

        return new RestProject(version, pom.projectName(), pom.description(), pom.java(), restDependencies);
    }
}
