package com.globaldashboard.dependencies.domain.port.secondary;

import com.globaldashboard.dependencies.domain.Objective;

import java.util.Collection;

public interface ObjectiveRepository {

    void save(Objective objective);

    Collection<Objective> getAllObjectives();
}
