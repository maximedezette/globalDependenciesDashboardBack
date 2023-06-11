package cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaldashboard.dependencies.domain.GroupId;
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
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectiveStepDefs {

    private final ObjectiveSpringRepository objectiveSpringRepository;

    private final ObjectMapper objectMapper;

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

    @When("I retrieve all objectives")
    public void iRetrieveAllObjectives() {
        RequestSpecification request = RestAssured.given();
        HttpStepDefs.response = request.get("/objectives");
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

    @Given("The following objectives are already set")
    public void theFollowingObjectivesAreAlreadySet(DataTable dataTable) {
        List<Map<String, String>> entries = dataTable.asMaps();

        entries.forEach(entry -> {
            ObjectiveEntity objectiveEntity = getObjectiveEntityFrom(entry);
            this.objectiveSpringRepository.save(objectiveEntity);
        });
    }

    @When("I delete the objective with these information")
    public void iDeleteTheObjectiveWithTheseInformation(DataTable dataTable) {
        List<Map<String, String>> entries = dataTable.asMaps();
        RequestSpecification request = RestAssured.given();

        entries.forEach(entry -> HttpStepDefs.response = request
                .header("Content-Type", "application/json")
                .delete("/objectives?group-id=" + entry.get("groupId") + "&artifact-id=" + entry.get("artifactId")));
    }

    @Then("I should retrieve the following objectives")
    public void iShouldRetrieveTheFollowingObjectives(DataTable dataTable) throws JsonProcessingException {
        List<Map<String, String>> entries = dataTable.asMaps();
        List<RestObjective> restObjectives = new ArrayList<>();
        entries.forEach(entry -> restObjectives.add(getRestObjectiveFrom(entry)));
        RequestSpecification request = RestAssured.given();
        HttpStepDefs.response = request.get("/objectives");
        String expectedObjectives = this.objectMapper.writeValueAsString(restObjectives);

        assertThat(HttpStepDefs.response.body().asString())
                .isEqualTo(expectedObjectives);
    }

    private ObjectiveEntity getObjectiveEntityFrom(Map<String, String> entry) {
        ObjectiveEntity objectiveEntity = new ObjectiveEntity();
        objectiveEntity.setGroupId(entry.get("groupId"));
        objectiveEntity.setArtifactId(entry.get("artifactId"));
        objectiveEntity.setVersion(entry.get("version"));
        return objectiveEntity;
    }

    private RestObjective getRestObjectiveFrom(Map<String, String> entry) {
        return new RestObjective(entry.get("groupId"), entry.get("artifactId"), entry.get("version"));
    }
}
