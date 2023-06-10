package com.globaldashboard.infrastructure.primary;

import com.globaldashboard.dependencies.domain.ProjectDescription;
import com.globaldashboard.dependencies.infrastructure.primary.RestProjectDescription;
import com.globaldashboard.fixture.ProjectDescriptionFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RestProjectTest {

    @Test
    void shouldConvertFromDomainProject() {
        ProjectDescription project = new ProjectDescription("projectName", ProjectDescriptionFixtures.DEFAULT_POM_URL);
        RestProjectDescription expectedRestProject = new RestProjectDescription("projectName", ProjectDescriptionFixtures.DEFAULT_POM_URL);

        RestProjectDescription restProject = RestProjectDescription.from(project);

        assertThat(restProject)
                .usingRecursiveComparison()
                .isEqualTo(expectedRestProject);
    }

    @Test
    void shouldConvertTpDomainProject() {
        ProjectDescription expectedProject = new ProjectDescription("projectName", ProjectDescriptionFixtures.DEFAULT_POM_URL);
        RestProjectDescription restProject = new RestProjectDescription("projectName", ProjectDescriptionFixtures.DEFAULT_POM_URL);

        ProjectDescription project = restProject.toDomain();

        assertThat(project)
                .usingRecursiveComparison()
                .isEqualTo(expectedProject);
    }

}