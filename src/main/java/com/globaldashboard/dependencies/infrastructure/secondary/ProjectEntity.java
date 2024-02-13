package com.globaldashboard.dependencies.infrastructure.secondary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.Project;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "project_dependency",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "dependency_id")
    )
    private Set<DependencyEntity> dependencies;

    public static ProjectEntity from(Project project) {
        Set<DependencyEntity> dependencyEntities = project.dependencies()
                .stream()
                .map(DependencyEntity::from)
                .collect(Collectors.toSet());

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setVersion(project.projectVersion().readableValue());
        projectEntity.setJavaVersion(project.java());
        projectEntity.setName(project.projectName());
        projectEntity.setDescription(project.description());
        projectEntity.setDependencies(dependencyEntities);
        projectEntity.setPomURL(project.pomURL());
        return projectEntity;
    }

    public Project toDomain() {

        List<Dependency> dependencyList = this.dependencies.stream()
                .map(DependencyEntity::toDomain)
                .toList();

        return Project.builder()
                .version(this.version)
                .name(this.name)
                .description(this.description)
                .javaVersion(this.javaVersion)
                .dependencies(dependencyList)
                .pomURL(this.pomURL)
                .build();
    }

    public long getId() {
        return id;
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
