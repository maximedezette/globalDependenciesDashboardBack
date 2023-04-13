package com.globaldashboard.dependencies.domain;

public record Objective(Dependency dependency, SemanticVersion version) {
    public boolean isAchievedBy(Dependency dependency) {
        SemanticVersion dependencyVersion = dependency
                .version()
                .orElseThrow(() -> new IllegalArgumentException("A dependency must have a version to know if it satisfies an objective."));

        return dependencyVersion
                .compareTo(this.version) >= 0;
    }
}
