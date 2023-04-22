package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.application.ObjectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/objectives")
public class ObjectiveResource {

    private final ObjectiveService objectiveService;

    @Autowired
    public ObjectiveResource(ObjectiveService objectiveService) {
        this.objectiveService = objectiveService;
    }

    @PostMapping
    public void saveObjective(@RequestBody RestObjective restObjective) {
        this.objectiveService.save(restObjective.toDomain());
    }

    @GetMapping
    public Collection<RestObjective> getAllObjectives() {
        return this.objectiveService.getAllObjectives()
                .stream()
                .map(RestObjective::from)
                .collect(Collectors.toSet());
    }

}