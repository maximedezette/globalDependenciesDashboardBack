package cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaldashboard.domain.port.secondary.ProjectRepository;
import com.globaldashboard.infrastructure.secondary.ProjectEntity;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectStepDefs {

    private ProjectRepository projectRepository;

    private ObjectMapper objectMapper;

    @Autowired
    public ProjectStepDefs(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.objectMapper = new ObjectMapper();
    }

    @When("A user asks for all projects")
    public void aUserAsksForAllProjects() {
        RequestSpecification request = RestAssured.given();

        HttpStepDefs.response = request
                .get("/projects");
    }

    @And("All the projects should be displayed")
    public void allTheProjectsShouldBeDisplayed() throws JsonProcessingException {
        String expectedProjects = this.objectMapper.writeValueAsString(getProjects());
        assertThat(HttpStepDefs.response.body().asString())
                .isEqualTo(expectedProjects);
    }

    @Given("There are projects stored in the database")
    public void thereAreProjectsStoredInTheDatabase() {
        this.projectRepository.saveAll(getProjects());
        this.projectRepository.flush();
    }

    private List<ProjectEntity> getProjects() {
        ProjectEntity aperoTech = new ProjectEntity();
        aperoTech.setName("AperoTech");
        aperoTech.setPomURL("https://github.com/maximedezette/globalDependenciesDashboardBack/blob/main/pom.xml");

        ProjectEntity kataApi = new ProjectEntity();
        kataApi.setName("KataApi");
        kataApi.setPomURL("https://github.com/maximedezette/kata-api/blob/main/pom.xml");

        return List.of(aperoTech, kataApi);
    }
}
