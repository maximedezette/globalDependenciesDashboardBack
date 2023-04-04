package com.globaldashboard.dependencies.domain.port.primary;

import com.globaldashboard.dependencies.domain.ProjectInformation;
import org.w3c.dom.Document;

public interface DependenciesFromPom {
    ProjectInformation parseXMLPOM(Document pomXML);
}
