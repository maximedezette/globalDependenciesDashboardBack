package com.globaldashboard.dependencies.infrastructure.secondary;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PomFactory {

    public Project getPomFrom(Map<String, Document> pomXMLs, String pomURL, String projectName) {

        String projectVersion = "";
        String description = "";
        String java = "";
        List<Dependency> dependencies = new ArrayList<>();

        for (Map.Entry<String, Document> entry : pomXMLs.entrySet()) {
            Document value = entry.getValue();
            Element documentElement = value.getDocumentElement();
            Node versionNode = documentElement.getElementsByTagName("version").item(1);
            Node descriptionNode = documentElement.getElementsByTagName("description").item(0);
            Node javaVersionNode = documentElement.getElementsByTagName("java.version").item(0);

            if (versionNode != null) {
                projectVersion = versionNode.getTextContent();
            }
            if (descriptionNode != null) {
                description = descriptionNode.getTextContent();
            }
            if (javaVersionNode != null) {
                java = javaVersionNode.getTextContent();
            }

            Map<String, String> properties = getProperties(documentElement, pomXMLs);
            dependencies.addAll(getDependencies(documentElement, properties));

        }

        return Project.builder()
                .version(projectVersion)
                .name(projectName)
                .description(description)
                .javaVersion(java)
                .dependencies(dependencies)
                .pomURL(pomURL)
                .build();
    }

    private Map<String, String> getProperties(Element documentElement, Map<String, Document> pomXMLs) {
        Map<String, String> properties = new HashMap<>();

        Node parentNode = documentElement.getElementsByTagName("parent").item(0);
        if (parentNode != null) {
            for (int i = 0; i < parentNode.getChildNodes().getLength(); i++) {
                Node childNode = parentNode.getChildNodes().item(i);
                if (childNode.getNodeName().equals("groupId")) {
                    Document parent = pomXMLs.get(childNode.getTextContent());
                    if (parent != null) {
                        properties.putAll(getProperties(parent.getDocumentElement(), pomXMLs));
                    }
                }
            }
        }
        Node propertiesRootNode = documentElement.getElementsByTagName("properties").item(0);
        if (propertiesRootNode != null) {
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

    private List<Dependency> getDependencies(Element documentElement, Map<String, String> properties) {
        NodeList dependencyNodes = documentElement.getElementsByTagName("dependency");
        int numberOfDependencies = dependencyNodes.getLength();

        List<Dependency> dependencies = new ArrayList<>();
        for (int i = 0; i < numberOfDependencies; i++) {
            Node currentDependencyNode = dependencyNodes.item(i);
            Dependency dependency = getDependency(currentDependencyNode, properties);
            dependencies.add(dependency);
        }
        return dependencies;
    }

    private Dependency getDependency(Node currentDependencyNode, Map<String, String> properties) {
        NodeList childNodes = currentDependencyNode.getChildNodes();
        int numberOfDependencyChilds = childNodes.getLength();
        String groupIdLabel = "";
        String artifactId = "";
        String version = "";
        for (int j = 0; j < numberOfDependencyChilds; j++) {
            Node childNode = childNodes.item(j);
            switch (childNode.getNodeName()) {
                case "groupId" -> groupIdLabel = childNode.getTextContent();
                case "artifactId" -> artifactId = childNode.getTextContent();
                case "version" -> version = getVersion(childNode.getTextContent(), properties);
            }
        }

        return Dependency.builder()
                .withGroupId(groupIdLabel)
                .withArtifactId(artifactId)
                .withVersion(version)
                .build();

    }

    private String getVersion(String version, Map<String, String> properties) {
        String propertyKey = version.replaceAll("\\$|\\{|\\}", "");
        if (properties.get(propertyKey) != null) {
            return properties.get(propertyKey);
        }
        return version;
    }
}
