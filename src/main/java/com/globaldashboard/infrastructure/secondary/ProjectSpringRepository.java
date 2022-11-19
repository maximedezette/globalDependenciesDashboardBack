package com.globaldashboard.infrastructure.secondary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectSpringRepository extends JpaRepository<ProjectEntity, UUID> {

    ProjectEntity findByName(String name);
    void deleteByName(String name);
}
