package com.globaldashboard.domain;

import java.util.Arrays;
import java.util.List;

public record SemanticVersion(int major, int minor, int patch, String label, String readableValue) {
    public static SemanticVersion from(String version) {
        List<Integer> versions = Arrays.stream(getVersions(version).split("\\."))
                .map(Integer::valueOf)
                .toList();


        return new SemanticVersion(versions.get(0), versions.get(1), versions.get(2), getLabel(version), version);
    }

    private static String getVersions(String version) {
        String[] splittedVersion = getSplittedVersion(version);

        return splittedVersion[0];
    }

    private static String getLabel(String version) {
        String[] splittedVersion = getSplittedVersion(version);
        String versionLabel = "";
        if (thereIsAdditionalLabels(splittedVersion)) {
            versionLabel = splittedVersion[1];
        }
        return versionLabel;
    }

    private static boolean thereIsAdditionalLabels(String[] splittedVersion) {
        return splittedVersion.length == 2;
    }


    private static String[] getSplittedVersion(String version) {
        return version.split("-");
    }

    @Override
    public String toString() {
        return readableValue;
    }
}
