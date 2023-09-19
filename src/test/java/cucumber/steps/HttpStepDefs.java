package cucumber.steps;

import cucumber.utils.HttpResponseDatatableComparator;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpStepDefs {
    public static Response response;

    @LocalServerPort
    private int port;

    private static String getResponseString() throws JSONException {
        JSONArray jsonArray = new JSONArray(HttpStepDefs.response.asString());
        return jsonArray.toString(4);
    }

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
    public void iShouldReceive(DataTable dataTable) throws JSONException {

        assertThat(HttpResponseDatatableComparator.match(dataTable, HttpStepDefs.response))
                .as("\n The provided datatable \n %s does not match the HttpResponse \n %s".formatted(dataTable, getResponseString()))
                .isTrue();

    }
}
