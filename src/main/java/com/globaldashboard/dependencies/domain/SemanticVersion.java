package com.globaldashboard.dependencies.domain;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public record SemanticVersion(int major, int minor, int patch, String label,
                              String readableValue) implements Comparable<SemanticVersion> {
    public static SemanticVersion from(String version) {
        if (version == null || version.length() == 0) {
            return defaultSemanticVersion();
        }

        List<Integer> versions = Arrays.stream(getVersions(version).split("\\."))
                .map(Integer::valueOf)
                .toList();


        return new SemanticVersion(versions.get(0), versions.get(1), versions.get(2), getLabel(version), version);
    }

    private static SemanticVersion defaultSemanticVersion() {
        return SemanticVersion.from("0.0.0");
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
        if (StringUtils.hasLength(this.label)) {
            return this.major + "." + this.minor + "." + this.patch + "-" + this.label;
        }
        return this.major + "." + this.minor + "." + this.patch;
    }

    @Override
    public int compareTo(SemanticVersion o) {
        return (this.major * 10000 - o.major * 10000)
                + (this.minor * 100 - o.minor * 100)
                + (this.patch - o.patch);
    }
}
