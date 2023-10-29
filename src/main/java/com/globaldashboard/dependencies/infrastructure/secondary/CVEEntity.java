package com.globaldashboard.dependencies.infrastructure.secondary;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CVE")
public class CVEEntity {

    @Id
    @Column(name = "identifier")
    private String identifier;

    @ManyToMany(mappedBy = "CVEList")
    private Set<DependencyEntity> dependencies = new HashSet<>();

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
