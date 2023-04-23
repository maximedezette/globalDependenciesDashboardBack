package cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaldashboard.dependencies.domain.Objective;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import com.globaldashboard.dependencies.infrastructure.primary.RestObjective;
import com.globaldashboard.dependencies.infrastructure.secondary.ObjectiveEntity;
import com.globaldashboard.dependencies.infrastructure.secondary.ObjectiveSpringRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectiveStepDefs {

    private final ObjectiveSpringRepository objectiveSpringRepository;

    private ObjectMapper objectMapper;

    @Autowired
    public ObjectiveStepDefs(ObjectiveSpringRepository objectiveSpringRepository) {
        this.objectiveSpringRepository = objectiveSpringRepository;
        this.objectMapper = new ObjectMapper();
    }

    @Before
    public void cleanObjectives() {
        this.objectiveSpringRepository.deleteAll();
    }

    @When("A user create an objective with these information")
    public void aUserCreateAnObjectiveWithTheseInformation(DataTable dataTable) {
        RequestSpecification request = RestAssured.given();
        Map<String, String> valueMap = dataTable.transpose().asMap();

        String groupId = valueMap.get("groupId");
        String artifactId = valueMap.get("artifactId");
        String version = valueMap.get("version");

        JSONObject requestParams = new JSONObject();
        requestParams.put("groupId", groupId);
        requestParams.put("artifactId", artifactId);
        requestParams.put("version", version);

        HttpStepDefs.response = request
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .post("/objectives");
    }

    @Then("The objective should be created")
    public void theObjectiveShouldBeCreated() {
        ObjectiveEntity expectedObjectiveEntity = new ObjectiveEntity();
        expectedObjectiveEntity.setId(1L);
        expectedObjectiveEntity.setGroupId("org.springframework.boot");
        expectedObjectiveEntity.setArtifactId("spring-boot-starter-parent");
        expectedObjectiveEntity.setVersion("2.6.1");

        ObjectiveEntity objectiveEntity = this.objectiveSpringRepository.findByGroupIdAndArtifactId("org.springframework.boot", "spring-boot-starter-parent");

        assertThat(objectiveEntity).isNotNull();
        assertThat(objectiveEntity)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedObjectiveEntity);
    }

    @Given("There are objectives stored in the database")
    public void thereAreObjectivesStoredInTheDatabase() {
        ObjectiveEntity objectiveEntity = new ObjectiveEntity();
        objectiveEntity.setGroupId("org.springframework.boot");
        objectiveEntity.setArtifactId("spring-boot-starter-parent");
        objectiveEntity.setVersion("2.6.1");

        ObjectiveEntity secondObjectiveEntity = new ObjectiveEntity();
        secondObjectiveEntity.setGroupId("io.cucumber");
        secondObjectiveEntity.setArtifactId("cucumber-bom");
        secondObjectiveEntity.setVersion("7.6.0");

        this.objectiveSpringRepository.save(objectiveEntity);
        this.objectiveSpringRepository.save(secondObjectiveEntity);
    }

    @When("I retrieve all objectives")
    public void iRetrieveAllObjectives() {
        RequestSpecification request = RestAssured.given();
        HttpStepDefs.response = request.get("/objectives");
    }

    @Then("I should receive all objectives")
    public void iShouldReceiveAllObjectives() throws JsonProcessingException {
        List<RestObjective> restObjectives = getRestObjectives();
        String expectedObjectives = this.objectMapper.writeValueAsString(restObjectives);

        assertThat(HttpStepDefs.response.body().asString())
                .isEqualTo(expectedObjectives);
    }

    private List<RestObjective> getRestObjectives() {
        Objective firstObjective = new Objective("io.cucumber", "cucumber-bom", SemanticVersion.from("7.6.0"));
        Objective secondObjective = new Objective("org.springframework.boot", "spring-boot-starter-parent", SemanticVersion.from("2.6.1"));

        return List.of(RestObjective.from(firstObjective), RestObjective.from(secondObjective));
    }

    @And("There are objectives stored in the database with these characteristics")
    public void thereAreObjectivesStoredInTheDatabaseWithTheseCharacteristics(DataTable dataTable) {
        List<Map<String, String>> valueMap = dataTable.entries();

        valueMap.forEach(entry -> {
            ObjectiveEntity objectiveEntity = new ObjectiveEntity();
            objectiveEntity.setGroupId(entry.get("groupId"));
            objectiveEntity.setArtifactId(entry.get("artifactId"));
            objectiveEntity.setVersion(entry.get("version"));

            this.objectiveSpringRepository.save(objectiveEntity);
        });

    }
}
