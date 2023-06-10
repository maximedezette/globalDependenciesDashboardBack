package com.globaldashboard.dependencies.domain.port.primary;

import com.globaldashboard.dependencies.domain.Project;
import org.w3c.dom.Document;

public interface DependenciesFromPom {
    Project parseXMLPOM(Document pomXML);
}
