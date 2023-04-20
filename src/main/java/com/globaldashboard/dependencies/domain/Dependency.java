package com.globaldashboard.dependencies.domain;

import java.util.Optional;

public record Dependency(String groupId, String artifactId, Optional<SemanticVersion> version) {

}
