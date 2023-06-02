package com.globaldashboard.dependencies.infrastructure.secondary;

import com.globaldashboard.dependencies.domain.Objective;
import com.globaldashboard.dependencies.domain.port.secondary.ObjectiveRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class InMemoryObjectiveRepository implements ObjectiveRepository {

    private final ObjectiveSpringRepository objectiveSpringRepository;

    public InMemoryObjectiveRepository(ObjectiveSpringRepository objectiveSpringRepository) {
        this.objectiveSpringRepository = objectiveSpringRepository;
    }

    @Override
    public void save(Objective objective) {
        this.objectiveSpringRepository.save(ObjectiveEntity.from(objective));
    }

    @Override
    public Collection<Objective> getAllObjectives() {
        return this.objectiveSpringRepository.findAll()
                .stream()
                .map(ObjectiveEntity::toDomain)
                .toList();
    }

    @Override
    public void delete(String groupId, String artifactId) {
        ObjectiveEntity objectiveEntity = this.objectiveSpringRepository.findByGroupIdAndArtifactId(groupId, artifactId);
        this.objectiveSpringRepository.delete(objectiveEntity);
    }
}
