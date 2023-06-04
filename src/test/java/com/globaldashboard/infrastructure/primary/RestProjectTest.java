package com.globaldashboard.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.infrastructure.primary.RestProject;
import com.globaldashboard.fixture.ProjectFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RestProjectTest {

    @Test
    void shouldConvertFromDomainProject() {
        Project project = ProjectFixtures.get();
        RestProject expectedRestProject = new RestProject("projectName", ProjectFixtures.DEFAULT_POM_URL);

        RestProject restProject = RestProject.from(project);

        assertThat(restProject)
                .usingRecursiveComparison()
                .isEqualTo(expectedRestProject);
    }

    @Test
    void shouldConvertTpDomainProject() {
        Project expectedProject = ProjectFixtures.get();
        RestProject restProject = new RestProject("projectName", ProjectFixtures.DEFAULT_POM_URL);

        Project project = restProject.toDomain();

        assertThat(project)
                .usingRecursiveComparison()
                .isEqualTo(expectedProject);
    }

}