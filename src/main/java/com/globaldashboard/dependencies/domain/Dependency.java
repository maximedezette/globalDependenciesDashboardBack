package com.globaldashboard.dependencies.domain;

import com.globaldashboard.domain.ArtifactId;

import java.util.Optional;

public record Dependency(GroupId groupId, ArtifactId artifactId, Optional<SemanticVersion> version) {

    public Dependency(String groupId, String artifactId) {
        this(new GroupId(groupId), new ArtifactId(artifactId), Optional.empty());
    }
    public Dependency(String groupId, String artifactId, String version) {
        this(new GroupId(groupId), new ArtifactId(artifactId), Optional.of(SemanticVersion.from(version)));
    }
}
