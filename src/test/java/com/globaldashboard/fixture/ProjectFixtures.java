package com.globaldashboard.fixture;

import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.domain.PomUrl;

public class ProjectFixtures {

    public static String DEFAULT_POM_URL = "https://raw.githubusercontent.com/maximedezette/globalDependenciesDashboardBack/main/src/test/java/com/globaldashboard/fixture/pom.xml";

    public static Project get() {
        return new Project("projectName", new PomUrl(DEFAULT_POM_URL));
    }
}
