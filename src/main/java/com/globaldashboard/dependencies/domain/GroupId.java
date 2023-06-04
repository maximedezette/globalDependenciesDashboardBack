package com.globaldashboard.dependencies.domain;

public record GroupId(String label) {

    public static final String VALID_GROUPID_REGEX = "[a-zA-Z0-9_.-]*";

    public GroupId {
        if (this.isInvalid(label)) {
            throw new IllegalArgumentException("The groupId %s is not valid.".formatted(label));
        }
    }

    private boolean isInvalid(String label) {
        return Character.isDigit(label.charAt(0))
                || !label.matches(VALID_GROUPID_REGEX);
    }
}
