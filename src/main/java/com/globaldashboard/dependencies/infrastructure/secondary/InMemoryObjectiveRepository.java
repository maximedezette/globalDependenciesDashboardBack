package com.globaldashboard.dependencies.infrastructure.secondary;

import com.globaldashboard.dependencies.domain.Objective;
import com.globaldashboard.dependencies.domain.port.secondary.ObjectiveRepository;
import org.springframework.stereotype.Repository;

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
}
