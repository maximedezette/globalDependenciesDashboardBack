package com.globaldashboard.domain.port.primary;

import com.globaldashboard.domain.Pom;
import org.w3c.dom.Document;

public interface DependenciesFromPom {
    Pom parseXMLPOM(Document pomXML);
}
