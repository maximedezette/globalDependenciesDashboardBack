package com.globaldashboard.dependencies.domain.port.secondary;

import com.globaldashboard.dependencies.domain.Project;

import java.util.List;

public interface ProjectRepository {
    Project findByName(String projectName);

    void deleteByName(String projectName);

    List<Project> findAll();

    void save(Project project);
}
