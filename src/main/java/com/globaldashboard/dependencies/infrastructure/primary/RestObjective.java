package com.globaldashboard.dependencies.infrastructure.primary;


import com.globaldashboard.dependencies.domain.GroupId;
import com.globaldashboard.dependencies.domain.Objective;
import com.globaldashboard.dependencies.domain.SemanticVersion;

public record RestObjective(String groupId, String artifactId, String version) {

    public static RestObjective from(Objective objective) {
        return new RestObjective(objective.groupId().label(), objective.artifactId(), objective.version().toString());
    }

    public Objective toDomain() {
        return new Objective(new GroupId(groupId), artifactId, SemanticVersion.from(version));
    }
}
