package com.globaldashboard.dependencies.domain.port.secondary;

import com.globaldashboard.dependencies.domain.ProjectDescription;

import java.util.Set;

public interface ProjectRepository {
    ProjectDescription findByName(String projectName);
    void deleteByName(String projectName);
    Set<ProjectDescription> findAll();
    void save(ProjectDescription project);
}
