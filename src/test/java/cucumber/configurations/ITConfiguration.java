package cucumber.configurations;

import com.globaldashboard.dependencies.domain.port.secondary.PomHttpRetriever;
import cucumber.mock.MockPomHttpRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ITConfiguration {

    @Bean
    public PomHttpRetriever pomHttpRetriever() {
        return new MockPomHttpRetriever();
    }
}
