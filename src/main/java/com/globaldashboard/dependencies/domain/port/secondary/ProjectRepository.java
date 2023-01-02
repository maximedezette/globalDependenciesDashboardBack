package com.globaldashboard.dependencies.domain.port.secondary;

import com.globaldashboard.dependencies.domain.Project;

import java.util.Set;

public interface ProjectRepository {
    Project findByName(String projectName);
    void deleteByName(String projectName);
    Set<Project> findAll();
    void save(Project project);
}
