package com.globaldashboard.dependencies.domain;

import com.globaldashboard.domain.ArtifactId;

public record Objective(GroupId groupId, ArtifactId artifactId, SemanticVersion version) {

    public Objective(String groupId, String artifactId, String version) {
        this(new GroupId(groupId), new ArtifactId(artifactId), SemanticVersion.from(version));
    }

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
