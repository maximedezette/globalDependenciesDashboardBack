package cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import com.globaldashboard.dependencies.infrastructure.primary.RestProjectDescription;
import com.globaldashboard.dependencies.infrastructure.secondary.ProjectEntity;
import com.globaldashboard.dependencies.infrastructure.secondary.ProjectSpringRepository;
import com.globaldashboard.fixture.DependencyFixture;
import com.globaldashboard.fixture.ProjectFixture;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectStepDefs {

    private final ProjectSpringRepository projectRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public ProjectStepDefs(ProjectSpringRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.objectMapper = new ObjectMapper();
    }

    @Before
    public void cleanProjects() {
        this.projectRepository.deleteAll();
    }

    @When("A user asks for all projects")
    public void aUserAsksForAllProjects() {
        RequestSpecification request = RestAssured.given();

        HttpStepDefs.response = request
                .get("/projects/simplified");
    }

    @And("All the projects should be displayed")
    public void allTheProjectsShouldBeDisplayed() throws JsonProcessingException {
        String expectedProjects = this.objectMapper.writeValueAsString(getProjectsDescription());
        assertThat(HttpStepDefs.response.body().asString())
                .isEqualTo(expectedProjects);
    }
    private List<RestProjectDescription> getProjectsDescription() {
        return getProjects()
                .stream()
                .map(ProjectEntity::toDomain)
                .map(RestProjectDescription::from)
                .toList();
    }

    @Given("There are projects stored in the database")
    public void thereAreProjectsStoredInTheDatabase() {
        this.projectRepository.saveAll(getProjects());
    }

    private List<ProjectEntity> getProjects() {
      return Stream.of(ProjectFixture.aperoTech(), ProjectFixture.kataApi())
              .map(ProjectEntity::from)
              .toList();
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
        assertThat(project.getPomURL()).isEqualTo(ProjectFixture.DEFAULT_POM_URL);
    }

    @Given("There is a project named {string} stored in the database")
    public void thereIsAProjectNamedStoredInTheDatabase(String name) {
        if (this.projectRepository.findByName(name) == null) {
            Project project = new Project(SemanticVersion.from("0.0.1-SNAPSHOT"), name, "Demo project for Apero Tech", "17", List.of(DependencyFixture.getCucumber()), ProjectFixture.DEFAULT_POM_URL );

            this.projectRepository.save(ProjectEntity.from(project));
        }
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

    @When("I ask the status of the first project")
    public void iAskTheStatusOfTheFirstProject() {
        RequestSpecification request = RestAssured.given();

        HttpStepDefs.response = request
                .get("/projects/AperoTech/status");
    }

    @Then("I should get the first project status")
    public void iShouldGetTheFirstProjectStatus() {
        assertThat(HttpStepDefs.response.statusCode())
                .isEqualTo(200);

        assertThat(HttpStepDefs.response.body().asString())
                .isEqualTo(getExpectedStatus());

    }

    private String getExpectedStatus() {
        return "[{\"groupId\":\"io.cucumber\",\"artifactId\":\"cucumber-bom\",\"version\":\"7.6.0\",\"isAchieved\":true},{\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-parent\",\"version\":\"2.7.0\",\"isAchieved\":false}]";
    }
}
