package com.globaldashboard.dependencies.infrastructure.secondary;

import com.globaldashboard.dependencies.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectSpringRepository extends JpaRepository<ProjectEntity, UUID> {

    ProjectEntity findByName(String name);
    void deleteByName(String name);
}
