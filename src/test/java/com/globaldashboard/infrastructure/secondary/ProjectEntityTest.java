package com.globaldashboard.infrastructure.secondary;

import com.globaldashboard.domain.Project;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectEntityTest {

    @Test
    void shouldConvertToDomainProject(){
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName("projectName");
        projectEntity.setPomURL("https://github.com/maximedezette/globalDependenciesDashboardBack/blob/main/pom.xml");

        Project project = new Project("projectName", "https://github.com/maximedezette/globalDependenciesDashboardBack/blob/main/pom.xml");

        assertThat(projectEntity.toDomain())
                .usingRecursiveComparison()
                .isEqualTo(project);
    }

    @Test
    void shouldConvertFromDomainProject(){
        ProjectEntity expectedProjectEntity = new ProjectEntity();
        expectedProjectEntity.setName("projectName");
        expectedProjectEntity.setPomURL("https://github.com/maximedezette/globalDependenciesDashboardBack/blob/main/pom.xml");
        Project project = new Project("projectName", "https://github.com/maximedezette/globalDependenciesDashboardBack/blob/main/pom.xml");

        ProjectEntity projectEntity = ProjectEntity.from(project);

        assertThat(projectEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedProjectEntity);
    }

}