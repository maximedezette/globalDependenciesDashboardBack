package com.globaldashboard.domain.port.secondary;

import com.globaldashboard.infrastructure.secondary.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {
    ProjectEntity findByName(String projectName);

    void deleteByName(String projectName);
}
