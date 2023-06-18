package com.globaldashboard.dependencies.domain;

import java.util.List;

public record Project(SemanticVersion projectVersion, String projectName, String description, String java, List<Dependency> dependencies,
                      String pomURL) {
}
