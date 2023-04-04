package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.ProjectInformation;

import java.util.Collection;

public record RestProjectInformation(String version, String projectName, String description, String java, Collection<Dependency> dependencies) {
    public static RestProjectInformation from(ProjectInformation pom) {
        String version = pom.projectVersion().toString();

        return new RestProjectInformation(version, pom.projectName(), pom.description(), pom.java(), pom.dependencies());
    }
}
