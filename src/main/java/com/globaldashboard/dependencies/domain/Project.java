package com.globaldashboard.dependencies.domain;

import java.util.List;

public record Project(SemanticVersion projectVersion, String projectName, String description, String java,
                      List<Dependency> dependencies,
                      String pomURL) {

    public Project(Builder builder) {
        this(
                SemanticVersion.from(builder.version),
                builder.name,
                builder.description,
                builder.javaVersion,
                builder.dependencies,
                builder.pomURL
        );

    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String version;
        private String name;
        private String description;
        private String javaVersion;
        private List<Dependency> dependencies;
        private String pomURL;

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder javaVersion(String javaVersion) {
            this.javaVersion = javaVersion;
            return this;
        }

        public Builder dependencies(List<Dependency> dependencies) {
            this.dependencies = dependencies;
            return this;
        }

        public Builder pomURL(String pomURL) {
            this.pomURL = pomURL;
            return this;
        }

        public Project build() {
            return new Project(this);
        }
    }

}
