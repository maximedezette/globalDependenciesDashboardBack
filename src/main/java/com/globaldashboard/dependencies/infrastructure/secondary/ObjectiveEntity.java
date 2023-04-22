package com.globaldashboard.dependencies.infrastructure.secondary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.globaldashboard.dependencies.domain.Objective;
import com.globaldashboard.dependencies.domain.SemanticVersion;

import javax.persistence.*;

@Entity
@Table(name = "objective")
public class ObjectiveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @Column(name = "artifact_id")
    private String artifactId;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "version")
    private String version;

    public static ObjectiveEntity from(Objective objective) {
        ObjectiveEntity objectiveEntity = new ObjectiveEntity();

        objectiveEntity.setGroupId(objective.groupId());
        objectiveEntity.setVersion(objective.version().toString());
        objectiveEntity.setArtifactId(objective.artifactId());

        return objectiveEntity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Objective toDomain() {
        return new Objective(this.groupId, this.artifactId, SemanticVersion.from(this.version));
    }
}
