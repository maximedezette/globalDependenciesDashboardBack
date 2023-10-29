package com.globaldashboard.dependencies.domain;

public record CVE(String identifier) {
    public CVE {
        if (!identifier.matches("^CVE-\\d{4}-\\d{4,}$")) {
            throw new IllegalArgumentException(identifier + " is not a valid CVE identifier !");
        }
    }
}
