package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.ProjectInformation;

import java.util.Collection;
import java.util.List;

public record RestProjectInformation(String version, String projectName, String description, String java,
                                     Collection<RestDependency> dependencies) {
    public static RestProjectInformation from(ProjectInformation pom) {
        String version = pom.projectVersion().toString();
        List<RestDependency> restDependencies = pom.dependencies().stream()
                .map(RestDependency::from)
                .toList();

        return new RestProjectInformation(version, pom.projectName(), pom.description(), pom.java(), restDependencies);
    }
}