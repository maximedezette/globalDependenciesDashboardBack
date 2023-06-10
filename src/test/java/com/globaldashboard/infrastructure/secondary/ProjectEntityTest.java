package com.globaldashboard.infrastructure.secondary;

import com.globaldashboard.dependencies.domain.ProjectDescription;
import com.globaldashboard.dependencies.infrastructure.secondary.ProjectEntity;
import com.globaldashboard.fixture.ProjectDescriptionFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectEntityTest {

    @Test
    void shouldConvertToDomainProject(){
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName("projectName");
        projectEntity.setPomURL(ProjectDescriptionFixtures.DEFAULT_POM_URL);

        ProjectDescription project = new ProjectDescription("projectName", ProjectDescriptionFixtures.DEFAULT_POM_URL);

        assertThat(projectEntity.toDomain())
                .usingRecursiveComparison()
                .isEqualTo(project);
    }

    @Test
    void shouldConvertFromDomainProject(){
        ProjectEntity expectedProjectEntity = new ProjectEntity();
        expectedProjectEntity.setName("projectName");
        expectedProjectEntity.setPomURL(ProjectDescriptionFixtures.DEFAULT_POM_URL);
        ProjectDescription project = new ProjectDescription("projectName", ProjectDescriptionFixtures.DEFAULT_POM_URL);

        ProjectEntity projectEntity = ProjectEntity.from(project);

        assertThat(projectEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedProjectEntity);
    }

}