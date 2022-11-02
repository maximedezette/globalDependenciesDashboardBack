package com.globaldashboard.domain.service;

import com.globaldashboard.domain.Dependency;
import com.globaldashboard.domain.Pom;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomainPomService implements PomService {

    @Override
    public Pom parseXMLPOM(Document pomXML) {
        Element documentElement = pomXML.getDocumentElement();

        String projectVersion = documentElement.getElementsByTagName("version").item(1).getTextContent();
        String projectName = documentElement.getElementsByTagName("name").item(0).getTextContent();
        String description = documentElement.getElementsByTagName("description").item(0).getTextContent();
        String java = documentElement.getElementsByTagName("java.version").item(0).getTextContent();
        int numberOfDependencies = documentElement.getElementsByTagName("dependency").getLength();

        List<Dependency> dependencies = getDependencies(documentElement, projectName, numberOfDependencies);


        return new Pom(projectVersion, projectName, description, java, dependencies);
    }

    private List<Dependency> getDependencies(Element documentElement, String projectName, int numberOfDependencies) {
        List<Dependency> dependencies = new ArrayList<>();
        for (int i = 0; i < numberOfDependencies; i++) {
            Node currendDependencyNode = documentElement.getElementsByTagName("dependency").item(i);
            Dependency dependency = getDependency(projectName, currendDependencyNode);
            dependencies.add(dependency);
        }
        return dependencies;
    }

    private Dependency getDependency(String projectName, Node currendDependencyNode) {
        int numberOfDependencyChilds = currendDependencyNode.getChildNodes().getLength();
        String groupId = "";
        String artifactId = "";
        String version = "";
        for (int j = 0; j < numberOfDependencyChilds; j++) {
            switch (currendDependencyNode.getChildNodes().item(j).getNodeName()) {
                case "groupId" -> groupId = currendDependencyNode.getChildNodes().item(j).getTextContent();
                case "artifactId" -> artifactId = currendDependencyNode.getChildNodes().item(j).getTextContent();
                case "version" -> version = currendDependencyNode.getChildNodes().item(j).getTextContent();
            }
        }
        return new Dependency(projectName,groupId, artifactId, version);
    }
}
