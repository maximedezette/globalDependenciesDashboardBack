package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.CVE;
import com.globaldashboard.dependencies.domain.Dependency;

import java.util.ArrayList;
import java.util.List;

public record RestDependency(String groupId, String artifactId, String version, List<String> CVEList) {
    public static RestDependency from(Dependency dependency) {

        String version = "";
        if (dependency.version().isPresent()) {
            version = dependency.version().get().toString();
        }

        List<String> identifiers = new ArrayList<>();
        boolean cveArePresent = dependency.CVEList().isPresent();
        if (cveArePresent) {
            identifiers = dependency.CVEList().get()
                    .stream()
                    .map(CVE::identifier)
                    .toList();
        }

        return new RestDependency(dependency.groupId().label(), dependency.artifactId().name(), version, identifiers);
    }
}
