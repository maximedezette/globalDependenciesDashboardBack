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

    public Pom getPomFrom(List<Document> pomXMLs) {

        String projectVersion = "";
        String projectName = "";
        String description = "";
        String java = "";
        List<Dependency> dependencies = new ArrayList<>();

        for (Document pomXML : pomXMLs) {
            Element documentElement = pomXML.getDocumentElement();
            Node versionNode = documentElement.getElementsByTagName("version").item(1);
            Node nameNode = documentElement.getElementsByTagName("name").item(0);
            Node descriptionNode = documentElement.getElementsByTagName("description").item(0);
            Node javaVersionNode = documentElement.getElementsByTagName("java.version").item(0);

            if (versionNode != null) {
                projectVersion = versionNode.getTextContent();
            }
            if (nameNode != null) {
                projectName = nameNode.getTextContent();
            }
            if (descriptionNode != null) {
                description = descriptionNode.getTextContent();
            }
            if (javaVersionNode != null) {
                java = javaVersionNode.getTextContent();
            }

            Map<String, String> properties = getProperties(documentElement);
            dependencies.addAll(getDependencies(documentElement, projectName, properties));
        }


        return new Pom(SemanticVersion.from(projectVersion), projectName, description, java, dependencies);
    }

    private Map<String, String> getProperties(Element documentElement) {
        Map<String, String> properties = new HashMap<>();
        Node propertiesRootNode = documentElement.getElementsByTagName("properties").item(0);
        if(propertiesRootNode != null ) {
            NodeList propertyNodes = propertiesRootNode.getChildNodes();
            int propertiesLength = propertyNodes.getLength();
            for (int i = 0; i < propertiesLength; i++) {
                Node node = propertyNodes.item(i);
                if (node != null) {
                    String propertyName = node.getNodeName();
                    String propertyContent = node.getTextContent();
                    properties.put(propertyName, propertyContent);
                }
            }
        }
        return properties;
    }

    private List<Dependency> getDependencies(Element documentElement, String projectName, Map<String, String> properties) {
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
        return new Dependency(projectName, groupId, artifactId, version);
    }

    private String getVersion(String version, Map<String, String> properties) {
        String propertyKey = version.replaceAll("\\$|\\{|\\}", "");
        if (properties.get(propertyKey) != null) {
            return properties.get(propertyKey);
        }
        return version;
    }
}
