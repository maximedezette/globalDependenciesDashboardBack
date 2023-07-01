package com.globaldashboard.domain;


import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public record ArtifactId(String name) {

    public ArtifactId {
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "Can't build an artifactId with no value");

        boolean isInvalid = Pattern.compile(".*[A-Z].*")
                .matcher(name)
                .matches();

        if(isInvalid) {
            throw new IllegalArgumentException("The provided name " + name + " does not correspond to a valid artifactId");
        }
    }

}
