package com.globaldashboard.fixture;

import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.domain.SemanticVersion;

import java.util.List;

public class ProjectFixture {

    public static Project aperoTech() {
        return new Project(SemanticVersion.from("0.0.1-SNAPSHOT"), "AperoTech", "Demo project for Apero Tech", "17", List.of(DependencyFixture.getCucumber()), ProjectDescriptionFixtures.DEFAULT_POM_URL );
    }

    public static Project kataApi() {
        return new Project(SemanticVersion.from("0.0.1-SNAPSHOT"), "KataApi", "Kata API", "17", List.of(), "https://github.com/maximedezette/kata-api/blob/main/pom.xml" );
    }

}
