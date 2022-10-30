package cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static org.assertj.core.api.Assertions.assertThat;

public class PomStepDefs {

    @When("A user asks for the dependencies of all projects")
    public void aUserAsksForTheDependenciesOfAllProjects() {
        RequestSpecification request = RestAssured.given();

        HttpStepDefs.response = request
                .get("/pom");
    }

    @And("All the dependencies should be displayed")
    public void allTheDependenciesShouldBeDisplayed() {
        assertThat(HttpStepDefs.response.body().asString())
                .isEqualTo(getExpectedDependencies());
    }

    private String getExpectedDependencies() {
        return "{\"projectVersion\":\"0.0.1-SNAPSHOT\",\"projectName\":\"aperotech\",\"description\":\"Demo project for Apero Tech\",\"java\":\"17\",\"dependencies\":[{\"projectName\":\"aperotech\",\"groupId\":\"io.cucumber\",\"artifactId\":\"cucumber-bom\",\"version\":\"7.6.0\"},{\"projectName\":\"aperotech\",\"groupId\":\"org.junit\",\"artifactId\":\"junit-bom\",\"version\":\"5.9.0\"},{\"projectName\":\"aperotech\",\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-data-jpa\",\"version\":\"\"},{\"projectName\":\"aperotech\",\"groupId\":\"org.apache.logging.log4j\",\"artifactId\":\"log4j\",\"version\":\"2.18.0\"},{\"projectName\":\"aperotech\",\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-data-rest\",\"version\":\"\"},{\"projectName\":\"aperotech\",\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-hateoas\",\"version\":\"\"},{\"projectName\":\"aperotech\",\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-web\",\"version\":\"\"},{\"projectName\":\"aperotech\",\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-devtools\",\"version\":\"\"},{\"projectName\":\"aperotech\",\"groupId\":\"com.h2database\",\"artifactId\":\"h2\",\"version\":\"\"},{\"projectName\":\"aperotech\",\"groupId\":\"org.postgresql\",\"artifactId\":\"postgresql\",\"version\":\"\"},{\"projectName\":\"aperotech\",\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-tomcat\",\"version\":\"\"},{\"projectName\":\"aperotech\",\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-test\",\"version\":\"\"},{\"projectName\":\"aperotech\",\"groupId\":\"io.cucumber\",\"artifactId\":\"cucumber-java\",\"version\":\"\"},{\"projectName\":\"aperotech\",\"groupId\":\"io.cucumber\",\"artifactId\":\"cucumber-junit-platform-engine\",\"version\":\"\"},{\"projectName\":\"aperotech\",\"groupId\":\"io.cucumber\",\"artifactId\":\"cucumber-spring\",\"version\":\"\"},{\"projectName\":\"aperotech\",\"groupId\":\"io.rest-assured\",\"artifactId\":\"rest-assured\",\"version\":\"4.2.0\"},{\"projectName\":\"aperotech\",\"groupId\":\"org.junit.jupiter\",\"artifactId\":\"junit-jupiter\",\"version\":\"\"},{\"projectName\":\"aperotech\",\"groupId\":\"org.junit.platform\",\"artifactId\":\"junit-platform-suite\",\"version\":\"\"}]}";
    }
}
