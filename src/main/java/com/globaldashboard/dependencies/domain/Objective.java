package com.globaldashboard.dependencies.domain;

public record Objective(GroupId groupId, String artifactId, SemanticVersion version) {
    public boolean isAchievedBy(Dependency dependency) {

        if (!dependency.groupId().equals(this.groupId) || !dependency.artifactId().equals(this.artifactId)) {
            return false;
        }

        SemanticVersion dependencyVersion = dependency
                .version()
                .orElseThrow(() -> new IllegalArgumentException("A dependency must have a version to know if it satisfies an objective."));

        return dependencyVersion
                .compareTo(this.version) >= 0;
    }
}
