package cucumber.steps;

import com.globaldashboard.dependencies.infrastructure.secondary.ObjectiveEntity;
import com.globaldashboard.dependencies.infrastructure.secondary.ObjectiveSpringRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectiveStepDefs {

    private final ObjectiveSpringRepository objectiveSpringRepository;

    @Autowired
    public ObjectiveStepDefs(ObjectiveSpringRepository objectiveSpringRepository) {
        this.objectiveSpringRepository = objectiveSpringRepository;
    }

    @Given("There are no objectives stored in the database")
    public void thereAreNoObjectivesStoredInTheDatabase() {
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
                .isEqualTo(expectedObjectiveEntity);
    }
}
