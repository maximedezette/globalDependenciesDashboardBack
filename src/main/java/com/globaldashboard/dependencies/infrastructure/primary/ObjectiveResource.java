package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.application.ObjectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
