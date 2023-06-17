package com.globaldashboard.fixture;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.GroupId;
import com.globaldashboard.dependencies.domain.SemanticVersion;

import java.util.Optional;

public class DependencyFixture {

    public static Dependency getCucumber() {
        return new Dependency(new GroupId("io.cucumber"), "cucumber-bom", Optional.of(SemanticVersion.from("7.6.0")));
    }
}
