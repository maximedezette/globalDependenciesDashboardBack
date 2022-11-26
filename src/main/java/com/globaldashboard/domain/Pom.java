package com.globaldashboard.domain;

import java.util.Collection;

public record Pom(SemanticVersion projectVersion, String projectName, String description, String java, Collection<Dependency> dependencies) {

}
