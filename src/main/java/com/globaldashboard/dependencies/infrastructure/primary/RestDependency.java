package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Dependency;

public record RestDependency(String groupId, String artifactId, String version) {
    public static RestDependency from(Dependency dependency) {

        String version = "";
        if (dependency.version().isPresent()) {
            version = dependency.version().get().toString();
        }
        
        return new RestDependency(dependency.groupId(), dependency.artifactId(), version);
    }
}
