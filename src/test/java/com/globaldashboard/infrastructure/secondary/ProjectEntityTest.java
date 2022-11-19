package com.globaldashboard.infrastructure.secondary;

import com.globaldashboard.domain.Project;
import com.globaldashboard.fixture.ProjectFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectEntityTest {

    @Test
    void shouldConvertToDomainProject(){
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName("projectName");
        projectEntity.setPomURL(ProjectFixtures.DEFAULT_POM_URL);

        Project project = new Project("projectName", ProjectFixtures.DEFAULT_POM_URL);

        assertThat(projectEntity.toDomain())
                .usingRecursiveComparison()
                .isEqualTo(project);
    }

    @Test
    void shouldConvertFromDomainProject(){
        ProjectEntity expectedProjectEntity = new ProjectEntity();
        expectedProjectEntity.setName("projectName");
        expectedProjectEntity.setPomURL(ProjectFixtures.DEFAULT_POM_URL);
        Project project = new Project("projectName", ProjectFixtures.DEFAULT_POM_URL);

        ProjectEntity projectEntity = ProjectEntity.from(project);

        assertThat(projectEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedProjectEntity);
    }

}