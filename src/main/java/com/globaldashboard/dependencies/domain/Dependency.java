package com.globaldashboard.dependencies.domain;

import java.util.Optional;

public record Dependency(GroupId groupId, String artifactId, Optional<SemanticVersion> version) {

}
