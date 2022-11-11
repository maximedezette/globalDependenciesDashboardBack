package com.globaldashboard.infrastructure.primary;

import com.globaldashboard.domain.Project;

public record RestProject(String name, String pomURL) {

    public static RestProject from(Project project) {
        return new RestProject(project.name(), project.pomURL());
    }
}
