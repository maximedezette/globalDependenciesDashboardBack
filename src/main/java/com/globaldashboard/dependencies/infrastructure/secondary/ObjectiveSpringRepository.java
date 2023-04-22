package com.globaldashboard.dependencies.infrastructure.secondary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ObjectiveSpringRepository extends JpaRepository<ObjectiveEntity, UUID> {

    ObjectiveEntity findByGroupIdAndArtifactId(String groupId, String artifactId);
}
