package com.globaldashboard.dependencies.application;

import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.domain.ProjectDescription;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import com.globaldashboard.dependencies.infrastructure.secondary.DependencyEntity;
import com.globaldashboard.dependencies.infrastructure.secondary.ProjectEntity;
import com.globaldashboard.dependencies.infrastructure.secondary.ProjectSpringRepository;
import com.globaldashboard.fixture.DependencyFixture;
import com.globaldashboard.fixture.ProjectDescriptionFixtures;
import org.junit.jupiter.api.BeforeEach;
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


    ProjectEntity savedProject;
    @BeforeEach
    void setUp() {
        Project project =  new Project(SemanticVersion.from("0.0.1-SNAPSHOT"), "AperoTech", "Demo project for Apero Tech", "17", List.of(DependencyFixture.getCucumber()));;
        ProjectDescription projectDescription = ProjectDescriptionFixtures.get();
        this.projectService.save(project, projectDescription);

        savedProject = projectSpringRepository.findByName(projectDescription.name());
    }

    @Test
    @Transactional
    void registerAProjectShouldStoreItsDependencies() {
        Set<DependencyEntity> dependencySet = savedProject.getDependencies();

        assertThat(dependencySet)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsOnly(DependencyEntity.from(DependencyFixture.getCucumber()));
    }

    @Test
    @Transactional
    void registerAProjectShouldStoreItsJavaVersion() {
        String javaVersion = savedProject.getJavaVersion();

        assertThat(javaVersion).isEqualTo("17");
    }
    @Test
    @Transactional
    void registerAProjectShouldStoreItsDescription() {
        String javaVersion = savedProject.getDescription();

        assertThat(javaVersion).isEqualTo("Demo project for Apero Tech");
    }

    @Test
    @Transactional
    void registerAProjectShouldStoreItsVersion() {
        String javaVersion = savedProject.getVersion();

        assertThat(javaVersion).isEqualTo("0.0.1-SNAPSHOT");
    }

}