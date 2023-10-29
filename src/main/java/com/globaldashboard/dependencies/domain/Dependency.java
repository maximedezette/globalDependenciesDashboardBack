package com.globaldashboard.dependencies.domain;

import com.globaldashboard.domain.ArtifactId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public record Dependency(GroupId groupId, ArtifactId artifactId, Optional<SemanticVersion> version,
                         Optional<List<CVE>> CVEList) {

    public Dependency(Builder builder) {
        this(builder.groupId, builder.artifactId, builder.version, builder.cveList);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private GroupId groupId;
        private ArtifactId artifactId;
        private Optional<SemanticVersion> version = Optional.empty();
        private Optional<List<CVE>> cveList = Optional.empty();

        public Builder withGroupId(String groupId) {
            this.groupId = new GroupId(groupId);
            return this;
        }

        public Builder withArtifactId(String artifactId) {
            this.artifactId = new ArtifactId(artifactId);
            return this;
        }

        public Builder withVersion(String version) {
            if (StringUtils.isNotBlank(version)) {
                this.version = Optional.ofNullable(SemanticVersion.from(version));
            }
            return this;
        }

        public Builder withCVEList(List<String> identifierList) {
            if (!CollectionUtils.isEmpty(identifierList)) {
                this.cveList = Optional.of(identifierList.stream()
                        .map(CVE::new)
                        .toList());
            }
            return this;
        }

        public Dependency build() {
            return new Dependency(this);
        }
    }
}
