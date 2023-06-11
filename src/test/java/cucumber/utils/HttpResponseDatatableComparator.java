package cucumber.utils;

import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class HttpResponseDatatableComparator {
    public static boolean match(DataTable dataTable, Response response) {
        String responseContent = response.getBody().asString();
        List<Map<String, String>> entries = dataTable.entries();
        List<String> stringsToMatch = new ArrayList<>();

        entries.forEach(entry -> stringsToMatch.add(getStringRepresentation(entry)));

        for (String stringToMatch : stringsToMatch) {
            if (!responseContent.contains(stringToMatch)) {
                return false;
            }
        }

        return true;
    }

    private static String getStringRepresentation(Map<String, String> entry) {
        return StringUtils.chop(entry.entrySet().stream()
                .map(transformToStringRepresentation())
                .reduce("", (a, b) -> a + b + ","));
    }


    private static Function<Map.Entry<String, String>, String> transformToStringRepresentation() {
        return entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"";
    }
}
