package com.globaldashboard.controller;

import com.globaldashboard.domain.Pom;
import com.globaldashboard.service.PomXMLToDomainPomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    private PomXMLToDomainPomService pomXMLToDomainPomService;

    @Autowired
    public PomController(PomXMLToDomainPomService pomXMLToDomainPomService) {
        this.pomXMLToDomainPomService = pomXMLToDomainPomService;
    }

    @GetMapping
    public Pom get() {

        String pomURL = "https://raw.githubusercontent.com/maximedezette/kata-api/main/pom.xml";
        URL url;
        try {
            url = new URL(pomURL);
            HttpURLConnection pomResponse = null;
            pomResponse = (HttpURLConnection) url.openConnection();
            pomResponse.setRequestMethod("GET");

            Document pomXML = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(pomResponse.getInputStream());
            pomResponse.disconnect();

            
            return this.pomXMLToDomainPomService.toDomainPom(pomXML);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
