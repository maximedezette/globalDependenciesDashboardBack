package com.globaldashboard.dependencies.domain;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

public record GroupId(String label) {

    public static final String VALID_GROUPID_REGEX = "[a-zA-Z0-9_.-]*";

    public GroupId {
        Preconditions.checkArgument(StringUtils.isNotBlank(label), "Can't create a groupId with no label");

        if (this.isInvalid(label)) {
            throw new IllegalArgumentException("The groupId %s is not valid.".formatted(label));
        }
    }

    private boolean isInvalid(String label) {
        return Character.isDigit(label.charAt(0))
                || !label.matches(VALID_GROUPID_REGEX);
    }
}
