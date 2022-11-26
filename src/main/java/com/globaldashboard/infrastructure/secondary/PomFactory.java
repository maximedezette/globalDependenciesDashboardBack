package com.globaldashboard.infrastructure.secondary;

import com.globaldashboard.domain.Dependency;
import com.globaldashboard.domain.Pom;
import com.globaldashboard.domain.SemanticVersion;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class PomFactory {

    public Pom getPomFrom(Document pomXML) {

        Element documentElement = pomXML.getDocumentElement();
        String projectVersion = documentElement.getElementsByTagName("version").item(1).getTextContent();
        String projectName = documentElement.getElementsByTagName("name").item(0).getTextContent();
        String description = documentElement.getElementsByTagName("description").item(0).getTextContent();
        String java = documentElement.getElementsByTagName("java.version").item(0).getTextContent();
        int numberOfDependencies = documentElement.getElementsByTagName("dependency").getLength();

        List<Dependency> dependencies = getDependencies(documentElement, projectName, numberOfDependencies);

        return new Pom(SemanticVersion.from(projectVersion), projectName, description, java, dependencies);
    }
    private List<Dependency> getDependencies(Element documentElement, String projectName, int numberOfDependencies) {
        List<Dependency> dependencies = new ArrayList<>();
        for (int i = 0; i < numberOfDependencies; i++) {
            Node currentDependencyNode = documentElement.getElementsByTagName("dependency").item(i);
            Dependency dependency = getDependency(projectName, currentDependencyNode);
            dependencies.add(dependency);
        }
        return dependencies;
    }

    private Dependency getDependency(String projectName, Node currentDependencyNode) {
        int numberOfDependencyChilds = currentDependencyNode.getChildNodes().getLength();
        String groupId = "";
        String artifactId = "";
        String version = "";
        for (int j = 0; j < numberOfDependencyChilds; j++) {
            switch (currentDependencyNode.getChildNodes().item(j).getNodeName()) {
                case "groupId" -> groupId = currentDependencyNode.getChildNodes().item(j).getTextContent();
                case "artifactId" -> artifactId = currentDependencyNode.getChildNodes().item(j).getTextContent();
                case "version" -> version = currentDependencyNode.getChildNodes().item(j).getTextContent();
            }
        }
        return new Dependency(projectName,groupId, artifactId, version);
    }
}
