package cucumber.mock;

import com.globaldashboard.dependencies.domain.ProjectInformation;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MockPomHttpRetrieverTest {

    @Test
    void shouldRetrievePomFromLocalFixture() {

        MockPomHttpRetriever mockPomHttpRetriever = new MockPomHttpRetriever();
        ProjectInformation projectInformation = mockPomHttpRetriever.getFromURL("http://pom.xml");

        assertThat(projectInformation.dependencies())
                .isNotNull()
                .hasSize(18);
    }
    @Test
    void shouldRetrieveHttpsPomFromLocalFixture() {

        MockPomHttpRetriever mockPomHttpRetriever = new MockPomHttpRetriever();
        ProjectInformation projectInformation = mockPomHttpRetriever.getFromURL("https://pom.xml");

        assertThat(projectInformation.dependencies())
                .isNotNull()
                .hasSize(18);
    }

    @Test
    void shouldRetrieveHttpsPomFromLocalFixtureGivingMultipleURL() {

        MockPomHttpRetriever mockPomHttpRetriever = new MockPomHttpRetriever();
        Set<ProjectInformation> projectInformation = mockPomHttpRetriever.getFromURLs(Set.of("https://pom.xml"));

        assertThat(projectInformation).hasSize(1);
    }

}