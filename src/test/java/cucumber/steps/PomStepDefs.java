package cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaldashboard.dependencies.infrastructure.primary.RestProject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static org.assertj.core.api.Assertions.assertThat;

public class PomStepDefs {

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

    @And("The project dependencies should be displayed")
    public void theProjectDependenciesShouldBeDisplayed() throws JsonProcessingException {
       RestProject restProject =  new ObjectMapper().readValue(HttpStepDefs.response.body().asString(), RestProject.class);
        assertThat(restProject.dependencies())
                .hasSize(1);
    }

    @And("All the dependencies should be displayed")
    public void allTheDependenciesShouldBeDisplayed() throws JsonProcessingException {
        RestProject[] restProject =  new ObjectMapper().readValue(HttpStepDefs.response.body().asString(), RestProject[].class);
        assertThat(restProject[0].dependencies())
                .hasSize(1);
    }
}
