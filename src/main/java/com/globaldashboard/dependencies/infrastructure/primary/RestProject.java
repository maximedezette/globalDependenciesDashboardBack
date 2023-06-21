package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Project;

import java.util.Collection;
import java.util.List;

public record RestProject(String version, String name, String description, String java, String pomURL,
                          Collection<RestDependency> dependencies) {
    public static RestProject from(Project project) {
        String version = project.projectVersion().toString();
        List<RestDependency> restDependencies = project.dependencies().stream()
                .map(RestDependency::from)
                .toList();

        return new RestProject(version, project.projectName(), project.description(), project.java(), project.pomURL(), restDependencies);
    }
}
