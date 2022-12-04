package com.globaldashboard.domain.port.secondary;

import com.globaldashboard.domain.Pom;
import com.globaldashboard.infrastructure.primary.exception.InvalidPomException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;

public interface PomHttpRetriever {
    Pom getFromURL(String url) throws InvalidPomException;

    Set<Pom> getFromURLs(Set<String> pomURLs);
}
