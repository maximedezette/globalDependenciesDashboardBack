package com.globaldashboard.domain;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DocumentFactory {

    public static Document getFromRawFile(String pomURL) throws IOException, ParserConfigurationException, SAXException {
        URL url = new URL(pomURL);
        HttpURLConnection pomResponse;
        pomResponse = (HttpURLConnection) url.openConnection();
        pomResponse.setRequestMethod("GET");
        Document pomXML = getPomXML(pomResponse);
        pomResponse.disconnect();

        return pomXML;
    }

    private static Document getPomXML(HttpURLConnection pomResponse) throws SAXException, IOException, ParserConfigurationException {
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(pomResponse.getInputStream());
    }
}
