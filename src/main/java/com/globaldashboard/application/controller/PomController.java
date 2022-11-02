package com.globaldashboard.application.controller;

import com.globaldashboard.domain.Pom;
import com.globaldashboard.domain.service.DomainPomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("pom")
public class PomController {

    private DomainPomService pomXMLToDomainPomService;

    @Autowired
    public PomController(DomainPomService pomXMLToDomainPomService) {
        this.pomXMLToDomainPomService = pomXMLToDomainPomService;
    }

    @GetMapping
    public Pom get() {

        String pomURL = "https://raw.githubusercontent.com/maximedezette/kata-api/main/pom.xml";
        URL url;
        try {
            url = new URL(pomURL);
            HttpURLConnection pomResponse;
            pomResponse = (HttpURLConnection) url.openConnection();
            pomResponse.setRequestMethod("GET");
            Document pomXML = getPomXML(pomResponse);
            pomResponse.disconnect();

            return this.pomXMLToDomainPomService.parseXMLPOM(pomXML);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private Document getPomXML(HttpURLConnection pomResponse) throws SAXException, IOException, ParserConfigurationException {
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(pomResponse.getInputStream());
    }
}

