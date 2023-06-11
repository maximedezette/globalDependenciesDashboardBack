package com.globaldashboard.fixture;

import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.domain.PomUrl;

public class ProjectFixtures {

    public static String DEFAULT_POM_URL = "https://pom.xml";

    public static Project get() {
        return new Project("projectName", new PomUrl(DEFAULT_POM_URL));
    }
}
