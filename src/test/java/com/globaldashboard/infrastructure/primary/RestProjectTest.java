package com.globaldashboard.infrastructure.primary;

import com.globaldashboard.domain.Project;
import com.globaldashboard.fixture.ProjectFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RestProjectTest {

    @Test
    void shouldConvertFromDomainProject() {
        Project project = new Project("projectName", ProjectFixtures.DEFAULT_POM_URL);
        RestProject expectedRestProject = new RestProject("projectName", ProjectFixtures.DEFAULT_POM_URL);

        RestProject restProject = RestProject.from(project);

        assertThat(restProject)
                .usingRecursiveComparison()
                .isEqualTo(expectedRestProject);
    }

    @Test
    void shouldConvertTpDomainProject() {
        Project expectedProject = new Project("projectName", ProjectFixtures.DEFAULT_POM_URL);
        RestProject restProject = new RestProject("projectName", ProjectFixtures.DEFAULT_POM_URL);

        Project project = restProject.toDomain();

        assertThat(project)
                .usingRecursiveComparison()
                .isEqualTo(expectedProject);
    }

}