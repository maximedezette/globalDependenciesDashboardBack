package cucumber.steps;

import cucumber.utils.HttpResponseDatatableComparator;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpStepDefs {
    public static Response response;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Then("He should have a success response")
    public void heShouldHaveASuccessResponse() {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    }


    @Then("I should receive")
    public void iShouldReceive(DataTable dataTable) {
        assertThat(HttpResponseDatatableComparator.match(dataTable, HttpStepDefs.response))
                .as("\n The provided datatable \n %s does not match the HttpResponse \n %s".formatted(dataTable, HttpStepDefs.response.asString()))
                .isTrue();

    }
}
