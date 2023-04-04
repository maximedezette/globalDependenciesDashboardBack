package com.globaldashboard.dependencies.domain.port.secondary;

import com.globaldashboard.dependencies.domain.ProjectInformation;
import com.globaldashboard.dependencies.infrastructure.primary.exception.InvalidPomException;

import java.util.Set;

public interface PomHttpRetriever {
    ProjectInformation getFromURL(String url) throws InvalidPomException;

    Set<ProjectInformation> getFromURLs(Set<String> pomURLs);
}
