package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Project;

public record RestProjectDescription(String name, String pomURL) {
    public static RestProjectDescription from(Project project) {
        return new RestProjectDescription(project.projectName(), project.pomURL());
    }

}
