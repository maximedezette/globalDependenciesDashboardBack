package com.globaldashboard.domain.port.secondary;

import com.globaldashboard.domain.Pom;
import com.globaldashboard.infrastructure.primary.exception.InvalidPomException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface PomHttpRetriever {
    Pom getFromURL(String url) throws InvalidPomException;
}
