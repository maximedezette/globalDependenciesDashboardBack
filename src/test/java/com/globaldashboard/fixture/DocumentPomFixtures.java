package com.globaldashboard.fixture;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DocumentPomFixtures {

    public static Document getPom() throws ParserConfigurationException, IOException, SAXException {
        File pomFile = new File("src/test/java/com/globaldashboard/fixture/pom.xml");
        FileInputStream fileInputStream = new FileInputStream(pomFile);

        return  DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(fileInputStream);
    }

    public static Document getChildPom() throws ParserConfigurationException, IOException, SAXException {
        File pomFile = new File("src/test/java/com/globaldashboard/fixture/child-pom.xml");
        FileInputStream fileInputStream = new FileInputStream(pomFile);

        return  DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(fileInputStream);
    }
}
