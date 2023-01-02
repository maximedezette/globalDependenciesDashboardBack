package com.globaldashboard.dependencies.domain.port.secondary;

import com.globaldashboard.dependencies.domain.Pom;
import com.globaldashboard.dependencies.infrastructure.primary.exception.InvalidPomException;

import java.util.Set;

public interface PomHttpRetriever {
    Pom getFromURL(String url) throws InvalidPomException;

    Set<Pom> getFromURLs(Set<String> pomURLs);
}
