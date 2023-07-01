package com.globaldashboard.domain;


import java.util.regex.Pattern;

public record ArtifactId(String name) {

    public ArtifactId {
        boolean isInvalid = Pattern.compile(".*[A-Z].*")
                .matcher(name)
                .matches();

        if(isInvalid) {
            throw new IllegalArgumentException("The provided name " + name + " does not correspond to a valid artifactId");
        }
    }

}
