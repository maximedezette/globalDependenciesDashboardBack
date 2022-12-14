package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Project;

public record RestProject(String name, String pomURL) {

    public static RestProject from(Project project) {
        return new RestProject(project.name(), project.pomURL());
    }

    public Project toDomain() {
        return new Project(this.name, this.pomURL);
    }
}
