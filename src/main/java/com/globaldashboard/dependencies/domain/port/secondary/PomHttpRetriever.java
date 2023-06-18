package com.globaldashboard.dependencies.domain.port.secondary;

import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.infrastructure.primary.exception.InvalidPomException;

import java.util.Set;

public interface PomHttpRetriever {
    Project getFromURL(String url, String name) throws InvalidPomException;
}
