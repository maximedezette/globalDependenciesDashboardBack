package com.globaldashboard.dependencies.domain;

import java.util.Optional;

public record Dependency(GroupId groupId, String artifactId, Optional<SemanticVersion> version) {

    public Dependency(String groupId, String artifactId) {
        this(new GroupId(groupId), artifactId, Optional.empty());
    }
    public Dependency(String groupId, String artifactId, String version) {
        this(new GroupId(groupId), artifactId, Optional.of(SemanticVersion.from(version)));
    }
}
