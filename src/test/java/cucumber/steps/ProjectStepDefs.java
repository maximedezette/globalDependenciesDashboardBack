package cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaldashboard.fixture.ProjectFixtures;
import com.globaldashboard.infrastructure.secondary.ProjectEntity;
import com.globaldashboard.infrastructure.secondary.ProjectSpringRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectStepDefs {

    private final ProjectSpringRepository projectRepository;

    private final ObjectMapper objectMapper;

    @BeforeEach
    public void cleanProjects() {
        this.projectRepository.deleteAll();
    }

    @Autowired
    public ProjectStepDefs(ProjectSpringRepository projectRepository) {
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
        aperoTech.setPomURL(ProjectFixtures.DEFAULT_POM_URL);

        ProjectEntity kataApi = new ProjectEntity();
        kataApi.setName("KataApi");
        kataApi.setPomURL("https://github.com/maximedezette/kata-api/blob/main/pom.xml");

        return List.of(aperoTech, kataApi);
    }

    @Given("There are not projects stored in the database")
    public void thereAreNotProjectsStoredInTheDatabase() {
        this.projectRepository.deleteAll();
    }

    @When("A user create a project with these information")
    public void aUserCreateAProjectWithTheseInformation(DataTable dataTable) {
        RequestSpecification request = RestAssured.given();
        Map<String, String> valueMap = dataTable.transpose().asMap();

        String projectName = valueMap.get("name");
        String pomURL = valueMap.get("pomURL");

        JSONObject requestParams = new JSONObject();
        requestParams.put("name", projectName);
        requestParams.put("pomURL", pomURL);

        HttpStepDefs.response = request
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .post("/projects");
    }
    @Then("The project should be created")
    public void theProjectShouldBeCreated() {
        ProjectEntity project = this.projectRepository.findByName("AperoTech");

        assertThat(project).isNotNull();
        assertThat(project.getName()).isEqualTo("AperoTech");
        assertThat(project.getPomURL()).isEqualTo(ProjectFixtures.DEFAULT_POM_URL);
    }

    @Given("There is a project named {string} stored in the database")
    public void thereIsAProjectNamedStoredInTheDatabase(String name) {
        ProjectEntity project = new ProjectEntity();
        project.setName(name);
        project.setPomURL(ProjectFixtures.DEFAULT_POM_URL);

        this.projectRepository.save(project);
    }

    @When("A user delete a project with name {string}")
    public void aUserDeleteAProjectWithName(String name) {
        RequestSpecification request = RestAssured.given();

        HttpStepDefs.response = request
                .header("Content-Type", "application/json")
                .delete("/projects/" + name);
    }

    @Then("The project {string} should be deleted")
    public void theProjectShouldBeDeleted(String name) {
        ProjectEntity project = this.projectRepository.findByName(name);

        assertThat(project).isNull();
    }
}
