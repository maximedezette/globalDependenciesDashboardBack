package com.globaldashboard.fixture;

import com.globaldashboard.dependencies.domain.Dependency;

public class DependencyFixture {

    public static Dependency getCucumber() {
        return new Dependency("io.cucumber", "cucumber-bom", "7.6.0");
    }
}
