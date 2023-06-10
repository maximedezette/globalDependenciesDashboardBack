package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.ProjectDescription;

public record RestProjectDescription(String name, String pomURL) {

    public static RestProjectDescription from(ProjectDescription project) {
        return new RestProjectDescription(project.name(), project.pomURL());
    }

    public ProjectDescription toDomain() {
        return new ProjectDescription(this.name, this.pomURL);
    }
}
