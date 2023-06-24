package cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.Project;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import com.globaldashboard.dependencies.infrastructure.primary.RestDependency;
import com.globaldashboard.dependencies.infrastructure.primary.RestProject;
import com.globaldashboard.dependencies.infrastructure.primary.RestProjectDescription;
import com.globaldashboard.dependencies.infrastructure.secondary.ProjectEntity;
import com.globaldashboard.dependencies.infrastructure.secondary.ProjectSpringRepository;
import com.globaldashboard.fixture.DependencyFixture;
import com.globaldashboard.fixture.ProjectFixture;
import cucumber.utils.DependencyDatatableConverter;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectStepDefs {

    private final ProjectSpringRepository projectRepository;

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    @Autowired
    public ProjectStepDefs(ProjectSpringRepository projectRepository) {
        this.projectRepository = projectRepository;
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
            Project project =  ProjectFixture
                    .fullBuilder()
                    .name(name)
                    .build();

            this.projectRepository.save(ProjectEntity.from(project));
        }
    }

    @Given("There is a project named {string} with dependencies")
    public void thereIsAProjectNamedWithDependencies(String name, DataTable dataTable) {
        List<Dependency> dependencies = DependencyDatatableConverter.getFrom(dataTable);

        if (this.projectRepository.findByName(name) == null) {
            Project project = ProjectFixture
                    .fullBuilder()
                    .name(name)
                    .dependencies(dependencies)
                    .build();

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

    @When("A user asks for the dependencies of the project named {string}")
    public void aUserAsksForTheDependenciesOfTheProjectNamed(String name) {
        RequestSpecification request = RestAssured.given();

        HttpStepDefs.response = request
                .get("/projects/"+ name);
    }
    @When("A user asks for all the dependencies")
    public void aUserAsksForAllTheDependencies() {
        RequestSpecification request = RestAssured.given();

        HttpStepDefs.response = request
                .get("/projects");
    }
    @Then("The following dependencies should be displayed")
    public void theFollowingDependenciesShouldBeDisplayed(DataTable dataTable) throws JsonProcessingException {

        List<RestDependency> expectedDependencies = DependencyDatatableConverter.getFrom(dataTable)
                .stream()
                .map(RestDependency::from)
                .toList();

        RestProject[] restProject = objectMapper.readValue(HttpStepDefs.response.body().asString(), RestProject[].class);

        List<RestDependency> restDependencies = Arrays.stream(restProject)
                .map(RestProject::dependencies)
                .flatMap(Collection::stream)
                .toList();

        assertThat(restDependencies)
                .containsExactlyInAnyOrderElementsOf(expectedDependencies);
    }

    private String getExpectedStatus() {
        return "[{\"groupId\":\"io.cucumber\",\"artifactId\":\"cucumber-bom\",\"version\":\"7.6.0\",\"isAchieved\":true},{\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-parent\",\"version\":\"2.7.0\",\"isAchieved\":false}]";
    }
}
