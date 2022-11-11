package com.globaldashboard.infrastructure.secondary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.globaldashboard.domain.Project;

import javax.persistence.*;

@Entity
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "pom_url")
    private String pomURL;

    public Project toDomain() {
        return new Project(this.name, this.pomURL);
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
}
