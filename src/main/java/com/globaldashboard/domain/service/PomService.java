package com.globaldashboard.domain.service;

import com.globaldashboard.domain.Pom;
import org.w3c.dom.Document;

public interface PomService {
    Pom parseXMLPOM(Document pomXML);
}
