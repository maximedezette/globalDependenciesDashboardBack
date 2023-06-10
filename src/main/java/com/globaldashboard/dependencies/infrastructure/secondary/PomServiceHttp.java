package com.globaldashboard.dependencies.infrastructure.secondary;

import com.globaldashboard.dependencies.domain.port.secondary.PomHttpRetriever;
import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.infrastructure.primary.exception.InvalidPomException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PomServiceHttp implements PomHttpRetriever {

    private final PomFactory pomFactory;

    public PomServiceHttp() {
        this.pomFactory = new PomFactory();
    }

    @Override
    public Project getFromURL(String url)  {

        URL pomURL;
        Document pomXML;
        try {
            pomURL = new URL(url);

            HttpURLConnection pomResponse = (HttpURLConnection) pomURL.openConnection();
            pomResponse.setRequestMethod("GET");
            pomXML = getPomXML(pomResponse);
            pomResponse.disconnect();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new InvalidPomException(e);
        }

        return this.pomFactory.getPomFrom(Map.of("", pomXML));
    }

    @Override
    public Set<Project> getFromURLs(Set<String> pomURLs) {
        return pomURLs.stream()
                .map(this::getFromURL)
                .collect(Collectors.toSet());
    }

    private Document getPomXML(HttpURLConnection pomResponse) throws SAXException, IOException, ParserConfigurationException {
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(pomResponse.getInputStream());
    }
}
