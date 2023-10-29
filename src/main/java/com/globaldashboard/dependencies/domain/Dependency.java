package com.globaldashboard.dependencies.domain;

import com.globaldashboard.domain.ArtifactId;

import java.util.List;
import java.util.Optional;

public record Dependency(GroupId groupId, ArtifactId artifactId, Optional<SemanticVersion> version,
                         Optional<List<CVE>> CVEList) {

    public Dependency(String groupId, String artifactId) {
        this(new GroupId(groupId), new ArtifactId(artifactId), Optional.empty(), Optional.empty());
    }

    public Dependency(String groupId, String artifactId, String version) {
        this(new GroupId(groupId), new ArtifactId(artifactId), Optional.of(SemanticVersion.from(version)), Optional.empty());
    }

    public Dependency(String groupId, String artifactId, String version, List<String> identifiers) {
        this(new GroupId(groupId), new ArtifactId(artifactId), Optional.of(SemanticVersion.from(version)), getCves(identifiers));
    }

    private static Optional<List<CVE>> getCves(List<String> identifiers) {
        if (identifiers == null || identifiers.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(identifiers
                .stream()
                .map(CVE::new)
                .toList());
    }
}
