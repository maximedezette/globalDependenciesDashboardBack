package com.globaldashboard.dependencies.application;

import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.domain.ProjectDescription;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import com.globaldashboard.dependencies.infrastructure.secondary.DependencyEntity;
import com.globaldashboard.dependencies.infrastructure.secondary.ProjectEntity;
import com.globaldashboard.dependencies.infrastructure.secondary.ProjectSpringRepository;
import com.globaldashboard.fixture.DependencyFixture;
import com.globaldashboard.fixture.ProjectDescriptionFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProjectServiceIT {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectSpringRepository projectSpringRepository;

    @Test
    @Transactional
    void registerAProjectShouldStoreItsDependencies() {
        Project project =  new Project(SemanticVersion.from("1.0.0"), "AperoTech", "Amazing blog", "21", List.of(DependencyFixture.getCucumber()));;
        ProjectDescription projectDescription = ProjectDescriptionFixtures.get();
        this.projectService.save(project, projectDescription);

        ProjectEntity savedProject = projectSpringRepository.findByName(project.projectName());
        Set<DependencyEntity> dependencySet = savedProject.getDependencies();

        assertThat(dependencySet)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsOnly(new DependencyEntity().from(DependencyFixture.getCucumber()));
    }

}