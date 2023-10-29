package com.globaldashboard.fixture;

import com.globaldashboard.dependencies.domain.CVE;

public class CVEFixture {

    public static String validIdentifier = "CVE-2023-35116";

    public static CVE cve() {
        return new CVE(validIdentifier);
    }
}
