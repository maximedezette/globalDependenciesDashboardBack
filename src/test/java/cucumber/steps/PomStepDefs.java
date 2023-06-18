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
    public void allTheDependenciesShouldBeDisplayed() {
        assertThat(HttpStepDefs.response.body().asString())
                .contains(getExpectedDependencies());
    }

    private String getExpectedDependencies() {
        return "{\"version\":\"0.0.1-SNAPSHOT\",\"projectName\":\"aperotech\",\"description\":\"Demo project for Apero Tech\",\"java\":\"17\",\"dependencies\":[{\"groupId\":\"io.cucumber\",\"artifactId\":\"cucumber-bom\",\"version\":\"7.6.0\"},{\"groupId\":\"org.junit\",\"artifactId\":\"junit-bom\",\"version\":\"5.9.0\"},{\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-data-jpa\",\"version\":\"\"},{\"groupId\":\"org.apache.logging.log4j\",\"artifactId\":\"log4j\",\"version\":\"2.18.0\"},{\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-data-rest\",\"version\":\"\"},{\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-hateoas\",\"version\":\"\"},{\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-web\",\"version\":\"\"},{\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-devtools\",\"version\":\"\"},{\"groupId\":\"com.h2database\",\"artifactId\":\"h2\",\"version\":\"\"},{\"groupId\":\"org.postgresql\",\"artifactId\":\"postgresql\",\"version\":\"\"},{\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-tomcat\",\"version\":\"\"},{\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-test\",\"version\":\"\"},{\"groupId\":\"io.cucumber\",\"artifactId\":\"cucumber-java\",\"version\":\"\"},{\"groupId\":\"io.cucumber\",\"artifactId\":\"cucumber-junit-platform-engine\",\"version\":\"\"},{\"groupId\":\"io.cucumber\",\"artifactId\":\"cucumber-spring\",\"version\":\"\"},{\"groupId\":\"io.rest-assured\",\"artifactId\":\"rest-assured\",\"version\":\"4.2.0\"},{\"groupId\":\"org.junit.jupiter\",\"artifactId\":\"junit-jupiter\",\"version\":\"\"},{\"groupId\":\"org.junit.platform\",\"artifactId\":\"junit-platform-suite\",\"version\":\"\"}]}";
    }
}
