package com.globaldashboard.dependencies.infrastructure.secondary;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.Pom;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PomFactory {

    public Pom getPomFrom(Document pomXML) {

        Element documentElement = pomXML.getDocumentElement();
        String projectVersion = documentElement.getElementsByTagName("version").item(1).getTextContent();
        String projectName = documentElement.getElementsByTagName("name").item(0).getTextContent();
        String description = documentElement.getElementsByTagName("description").item(0).getTextContent();
        String java = documentElement.getElementsByTagName("java.version").item(0).getTextContent();

        Map<String, String> properties = getProperties(documentElement);
        List<Dependency> dependencies = getDependencies(documentElement, projectName, properties);


        return new Pom(SemanticVersion.from(projectVersion), projectName, description, java, dependencies);
    }

    private Map<String, String>  getProperties(Element documentElement) {
        Map<String, String> properties = new HashMap<>();
        NodeList propertyNodes = documentElement.getElementsByTagName("properties").item(0).getChildNodes();
        int propertiesLength = propertyNodes.getLength();
        for (int i = 0; i < propertiesLength; i++) {
            Node node = propertyNodes.item(i);
            if(node != null) {
                String propertyName = node.getNodeName();
                String propertyContent = node.getTextContent();
                properties.put(propertyName, propertyContent);
            }
        }
        return properties;
    }

    private List<Dependency> getDependencies(Element documentElement, String projectName, Map<String,String> properties) {
        NodeList dependencyNodes = documentElement.getElementsByTagName("dependency");
        int numberOfDependencies = dependencyNodes.getLength();

        List<Dependency> dependencies = new ArrayList<>();
        for (int i = 0; i < numberOfDependencies; i++) {
            Node currentDependencyNode = dependencyNodes.item(i);
            Dependency dependency = getDependency(projectName, currentDependencyNode, properties);
            dependencies.add(dependency);
        }
        return dependencies;
    }

    private Dependency getDependency(String projectName, Node currentDependencyNode, Map<String, String> properties) {
        NodeList childNodes = currentDependencyNode.getChildNodes();
        int numberOfDependencyChilds = childNodes.getLength();
        String groupId = "";
        String artifactId = "";
        String version = "";
        for (int j = 0; j < numberOfDependencyChilds; j++) {
            Node childNode = childNodes.item(j);
            switch (childNode.getNodeName()) {
                case "groupId" -> groupId = childNode.getTextContent();
                case "artifactId" -> artifactId = childNode.getTextContent();
                case "version" -> version = getVersion(childNode.getTextContent(), properties);
            }
        }
        return new Dependency(projectName,groupId, artifactId, version);
    }

    private String getVersion(String version, Map<String, String> properties) {
        String propertyKey = version.replaceAll("\\$|\\{|\\}", "");
        if(properties.get(propertyKey) != null) {
            return properties.get(propertyKey);
        }
        return version;
    }
}
