package com.globaldashboard.domain;

import java.util.Collection;
import java.util.List;

public record Pom(String projectVersion, String projectName, String description, String java, Collection<Dependency> dependencies) {

}
