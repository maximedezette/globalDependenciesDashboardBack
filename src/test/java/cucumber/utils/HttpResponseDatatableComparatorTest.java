package cucumber.utils;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableTypeRegistry;
import io.cucumber.datatable.DataTableTypeRegistryTableConverter;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseDatatableComparatorTest {

    @Test
    void shouldIndicateThatAResponseDoesMatchDatatable() {
        List<String> header = List.of("groupId", "artifactId", "version");
        List<String> content = List.of("org.springframework.boot", "spring-boot-starter-parent", "2.6.1");
        DataTableTypeRegistryTableConverter tableConverter = new DataTableTypeRegistryTableConverter(new DataTableTypeRegistry(Locale.FRANCE));
        DataTable dataTable =  DataTable.create(List.of(header, content), tableConverter);
        Response response = getResponse();

        boolean responseMatchDatable = HttpResponseDatatableComparator.match(dataTable, response);

        assertThat(responseMatchDatable).isTrue();
    }
    @Test
    void shouldIndicateThatAResponseDoesMatchDatatableMultipleContent() {
        List<String> header = List.of("groupId", "artifactId", "version");
        List<String> firstLine = List.of("org.springframework.boot", "spring-boot-starter-parent", "2.6.1");
        List<String> secondLine = List.of("io.cucumber", "cucumber-bom", "7.6.0");
        DataTableTypeRegistryTableConverter tableConverter = new DataTableTypeRegistryTableConverter(new DataTableTypeRegistry(Locale.FRANCE));
        DataTable dataTable =  DataTable.create(List.of(header, firstLine, secondLine), tableConverter);
        Response response = getResponse();

        boolean responseMatchDatable = HttpResponseDatatableComparator.match(dataTable, response);

        assertThat(responseMatchDatable).isTrue();
    }
    @Test
    void shouldIndicateThatAResponseDoesNotMatchDatatable() {
        List<String> header = List.of("groupId", "artifactId", "version");
        List<String> content = List.of("not-existing", "spring-boot-starter-parent", "2.6.1");
        DataTableTypeRegistryTableConverter tableConverter = new DataTableTypeRegistryTableConverter(new DataTableTypeRegistry(Locale.FRANCE));
        DataTable dataTable =  DataTable.create(List.of(header, content), tableConverter);
        Response response = getResponse();

        boolean responseMatchDatable = HttpResponseDatatableComparator.match(dataTable, response);

        assertThat(responseMatchDatable).isFalse();
    }

    private static Response getResponse() {
        Response response = Mockito.mock(RestAssuredResponseImpl.class);
        ResponseBody responseBody = Mockito.mock(ResponseBody.class);
        String responseBodyContent = "[{\"groupId\":\"io.cucumber\",\"artifactId\":\"cucumber-bom\",\"version\":\"7.6.0\"},{\"groupId\":\"org.springframework.boot\",\"artifactId\":\"spring-boot-starter-parent\",\"version\":\"2.6.1\"}]";
        Mockito.when(responseBody.asString()).thenReturn(responseBodyContent);
        Mockito.when(response.getBody()).thenReturn(responseBody);
        return response;
    }

}