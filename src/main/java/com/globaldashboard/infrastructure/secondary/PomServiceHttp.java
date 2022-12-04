package com.globaldashboard.infrastructure.secondary;

import com.globaldashboard.domain.Pom;
import com.globaldashboard.domain.port.secondary.PomHttpRetriever;
import com.globaldashboard.infrastructure.primary.exception.InvalidPomException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PomServiceHttp implements PomHttpRetriever {

    private final PomFactory pomFactory;

    public PomServiceHttp() {
        this.pomFactory = new PomFactory();
    }

    @Override
    public Pom getFromURL(String url)  {

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

        return this.pomFactory.getPomFrom(pomXML);
    }

    @Override
    public Set<Pom> getFromURLs(Set<String> pomURLs) {
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
