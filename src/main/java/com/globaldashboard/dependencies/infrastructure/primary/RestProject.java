package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.domain.PomUrl;

public record RestProject(String name, String pomURL) {

    public static RestProject from(Project project) {
        return new RestProject(project.name(), project.pomURL().url());
    }

    public Project toDomain() {
        return new Project(this.name, new PomUrl(this.pomURL));
    }
}
