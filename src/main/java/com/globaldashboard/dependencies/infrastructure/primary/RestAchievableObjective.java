package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Objective;

public record RestAchievableObjective(String groupId, String artifactId, String version, Boolean isAchieved) {

    public static RestAchievableObjective from(Objective objective, Boolean isAchieved) {
        return new RestAchievableObjective(objective.groupId().label(), objective.artifactId(), objective.version().toString(), isAchieved);
    }
}
