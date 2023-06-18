package com.globaldashboard.infrastructure.secondary;

import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.domain.ProjectDescription;
import com.globaldashboard.dependencies.infrastructure.secondary.DependencyEntity;
import com.globaldashboard.dependencies.infrastructure.secondary.ProjectEntity;
import com.globaldashboard.fixture.DependencyFixture;
import com.globaldashboard.fixture.ProjectDescriptionFixtures;
import com.globaldashboard.fixture.ProjectFixture;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectEntityTest {

    @Test
    void shouldConvertToDomainProjectDescription(){
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName("projectName");
        projectEntity.setPomURL(ProjectDescriptionFixtures.DEFAULT_POM_URL);

        ProjectDescription project = new ProjectDescription("projectName", ProjectDescriptionFixtures.DEFAULT_POM_URL);

        assertThat(projectEntity.toProjectDescriptionDomain())
                .usingRecursiveComparison()
                .isEqualTo(project);
    }

    @Test
    void shouldConvertFromDomainProjectDescription(){
        ProjectEntity expectedProjectEntity = new ProjectEntity();
        expectedProjectEntity.setName("projectName");
        expectedProjectEntity.setPomURL(ProjectDescriptionFixtures.DEFAULT_POM_URL);
        ProjectDescription project = new ProjectDescription("projectName", ProjectDescriptionFixtures.DEFAULT_POM_URL);

        ProjectEntity projectEntity = ProjectEntity.fromProjectDescription(project);

        assertThat(projectEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedProjectEntity);
    }

    @Test
    void shouldConvertToDomain(){
        ProjectEntity projectEntity = getProjectEntity();
        Project expectedProject = ProjectFixture.aperoTech();

        Project actualProject = projectEntity.toDomain();

        assertThat(actualProject)
                .usingRecursiveComparison()
                .isEqualTo(expectedProject);
    }

    @Test
    void shouldBeBuildableFromDomain(){
        ProjectEntity expectedProjectEntity = getProjectEntity();
        Project project = ProjectFixture.aperoTech();

        ProjectEntity actualProjectEntity = ProjectEntity.from(project);

        assertThat(actualProjectEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedProjectEntity);
    }

    private static ProjectEntity getProjectEntity() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setVersion("0.0.1-SNAPSHOT");
        projectEntity.setName("AperoTech");
        projectEntity.setDescription("Demo project for Apero Tech");
        projectEntity.setJavaVersion("17");
        projectEntity.setDependencies(Set.of(DependencyEntity.from(DependencyFixture.getCucumber())));
        projectEntity.setPomURL(ProjectDescriptionFixtures.DEFAULT_POM_URL);
        return projectEntity;
    }

}