package com.globaldashboard.infrastructure.primary;

import com.globaldashboard.domain.Dependency;
import com.globaldashboard.domain.Pom;

import java.util.Collection;

public record RestPom(String version, String projectName, String description, String java, Collection<Dependency> dependencies) {
    public static RestPom from(Pom pom) {
        String version = pom.projectVersion().toString();

        return new RestPom(version, pom.projectName(), pom.description(), pom.java(), pom.dependencies());
    }
}
