package com.globaldashboard.dependencies.infrastructure.primary;

import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.fixture.ProjectFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RestProjectDescriptionTest {

    @Test
    void shouldConvertFromDomainProject() {
        Project project = ProjectFixture.aperoTech();
        RestProjectDescription expectedRestProject = new RestProjectDescription("AperoTech", ProjectFixture.DEFAULT_POM_URL);

        RestProjectDescription restProject = RestProjectDescription.from(project);

        assertThat(restProject)
                .usingRecursiveComparison()
                .isEqualTo(expectedRestProject);
    }
}