package cucumber.configurations;

import com.globaldashboard.GlobalDependenciesDashboardApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = GlobalDependenciesDashboardApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {ITConfiguration.class})
public class CucumberSpringConfiguration {
}
