package com.globaldashboard.dependencies.domain;

import java.util.List;

public record ProjectInformation(SemanticVersion projectVersion, String projectName, String description, String java, List<Dependency> dependencies) {

}
