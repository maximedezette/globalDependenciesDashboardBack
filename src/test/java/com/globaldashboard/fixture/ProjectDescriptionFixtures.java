package com.globaldashboard.fixture;

import com.globaldashboard.dependencies.domain.ProjectDescription;
import com.globaldashboard.domain.PomUrl;

public class ProjectDescriptionFixtures {

    public static String DEFAULT_POM_URL = "https://raw.githubusercontent.com/maximedezette/globalDependenciesDashboardBack/main/src/test/java/com/globaldashboard/fixture/pom.xml";

    public static ProjectDescription get() {
        return new ProjectDescription("projectName", new PomUrl(DEFAULT_POM_URL).url());
    }
}
