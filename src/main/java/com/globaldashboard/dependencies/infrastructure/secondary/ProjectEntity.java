package com.globaldashboard.dependencies.infrastructure.secondary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.globaldashboard.dependencies.domain.ProjectDescription;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "pom_url")
    private String pomURL;

    @Column(name = "version")
    private String version;
    @Column(name = "java_version")
    private String javaVersion;
    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "project_dependency",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "dependency_id")
    )
    private Set<DependencyEntity> dependencies;

    public ProjectDescription toDomain() {
        return new ProjectDescription(this.name, this.pomURL);
    }

    public static ProjectEntity from(ProjectDescription project) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(project.name());
        projectEntity.setPomURL(project.pomURL());

        return projectEntity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPomURL() {
        return pomURL;
    }

    public void setPomURL(String pomURL) {
        this.pomURL = pomURL;
    }

    public Set<DependencyEntity> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<DependencyEntity> dependencies) {
        this.dependencies = dependencies;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
