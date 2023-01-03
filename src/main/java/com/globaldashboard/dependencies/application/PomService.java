package com.globaldashboard.dependencies.application;

import com.globaldashboard.dependencies.domain.Pom;
import com.globaldashboard.dependencies.domain.port.primary.DependenciesFromPom;
import com.globaldashboard.dependencies.infrastructure.secondary.PomFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.util.List;

@Service
public class PomService implements DependenciesFromPom {

    private final PomFactory pomFactory;

    public PomService() {
        this.pomFactory = new PomFactory();
    }

    @Override
    public Pom parseXMLPOM(Document pomXML) {
        return this.pomFactory.getPomFrom(List.of(pomXML));
    }
}
