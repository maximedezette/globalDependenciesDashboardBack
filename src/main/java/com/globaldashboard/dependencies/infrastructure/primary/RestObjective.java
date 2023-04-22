package com.globaldashboard.dependencies.infrastructure.primary;


import com.globaldashboard.dependencies.domain.Objective;
import com.globaldashboard.dependencies.domain.SemanticVersion;

public record RestObjective(String groupId, String artifactId, String version) {

    public static RestObjective from(Objective objective) {
        return new RestObjective(objective.groupId(), objective.artifactId(), objective.version().toString());
    }

    public Objective toDomain() {
        return new Objective(groupId, artifactId, SemanticVersion.from(version));
    }
}
