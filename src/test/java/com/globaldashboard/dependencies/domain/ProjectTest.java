package com.globaldashboard.dependencies.domain;

import com.globaldashboard.fixture.DependencyFixture;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectTest {

    @Test
    void shouldBuildProject() {
        Project expected = new Project(SemanticVersion.from("2.1.0-SNAPSHOT"), "AperoTech", "description", "21",
                List.of(DependencyFixture.getCucumber()), "http://pom.xml");

        Project project = Project.builder()
                .version("2.1.0-SNAPSHOT")
                .name("AperoTech")
                .description("description")
                .javaVersion("21")
                .dependencies(List.of(DependencyFixture.getCucumber()))
                .pomURL("http://pom.xml")
                .build();


        assertThat(project).isEqualTo(expected);
    }

}