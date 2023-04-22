package com.globaldashboard.dependencies.application;

import com.globaldashboard.dependencies.domain.Objective;
import com.globaldashboard.dependencies.domain.port.secondary.ObjectiveRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ObjectiveService {

    private final ObjectiveRepository objectiveRepository;

    public ObjectiveService(ObjectiveRepository objectiveRepository) {
        this.objectiveRepository = objectiveRepository;
    }

    public void save(Objective objective) {
        this.objectiveRepository.save(objective);
    }

    public Collection<Objective> getAllObjectives() {
        return this.objectiveRepository.getAllObjectives();
    }
}
