package com.globaldashboard.application;

import com.globaldashboard.domain.Pom;
import com.globaldashboard.domain.PomFactory;
import com.globaldashboard.domain.port.primary.DependenciesFromPom;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
public class PomService implements DependenciesFromPom {

    private final PomFactory pomFactory;

    public PomService() {
        this.pomFactory = new PomFactory();
    }

    @Override
    public Pom parseXMLPOM(Document pomXML) {
        return this.pomFactory.getPomFrom(pomXML);
    }
}
