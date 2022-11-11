package com.globaldashboard.infrastructure.primary;

import com.globaldashboard.domain.Project;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RestProjectTest {

    @Test
    void shouldConvertFromDomainProject() {
        Project project = new Project("projectName", "https://github.com/maximedezette/globalDependenciesDashboardBack/blob/main/pom.xml");
        RestProject expectedRestProject = new RestProject("projectName", "https://github.com/maximedezette/globalDependenciesDashboardBack/blob/main/pom.xml");

        RestProject restProject = RestProject.from(project);

        assertThat(restProject)
                .usingRecursiveComparison()
                .isEqualTo(expectedRestProject);
    }

}