package com.globaldashboard.dependencies.domain.port.primary;

import com.globaldashboard.dependencies.domain.Pom;
import org.w3c.dom.Document;

public interface DependenciesFromPom {
    Pom parseXMLPOM(Document pomXML);
}
