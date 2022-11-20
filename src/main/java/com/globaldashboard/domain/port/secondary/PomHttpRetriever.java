package com.globaldashboard.domain.port.secondary;

import com.globaldashboard.domain.Pom;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface PomHttpRetriever {
    Pom getFromURL(String url) throws IOException, ParserConfigurationException, SAXException;
}
