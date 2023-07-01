package com.globaldashboard.dependencies.infrastructure.secondary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.GroupId;
import com.globaldashboard.dependencies.domain.SemanticVersion;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "dependency")
public class DependencyEntity {
    public static final String NO_VERSION = "0.0.0";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "artifact_id")
    private String artifactId;

    @Column(name = "version")
    private String version;

    @ManyToMany(mappedBy = "dependencies")
    private Set<ProjectEntity> projects = new HashSet<>();

    public static DependencyEntity from(Dependency dependency) {
        DependencyEntity dependencyEntity = new DependencyEntity();
        dependencyEntity.setArtifactId(dependency.artifactId().name());
        dependencyEntity.setGroupId(dependency.groupId().label());
        SemanticVersion version = dependency.version().orElse(SemanticVersion.from(""));
        dependencyEntity.setVersion(version.readableValue());
        return dependencyEntity;
    }

    public Dependency toDomain() {
        if (this.version.equals(NO_VERSION)) {
            return new Dependency(this.groupId, this.artifactId);
        }

        return new Dependency(this.groupId, this.artifactId, this.getVersion());
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String name) {
        this.groupId = name;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String pomURL) {
        this.artifactId = pomURL;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
