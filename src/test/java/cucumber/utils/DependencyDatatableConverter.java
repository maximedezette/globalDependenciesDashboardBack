package cucumber.utils;

import com.globaldashboard.dependencies.domain.Dependency;
import io.cucumber.datatable.DataTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DependencyDatatableConverter {

    public static List<Dependency> getFrom(DataTable dataTable) {
        List<Dependency> dependencies = new ArrayList<>();
        dataTable.entries().forEach(entry -> {
            dependencies.add(getDependency(entry));
        });

        return dependencies;
    }

    private static Dependency getDependency(Map<String, String> entry) {
        List<String> CVE = List.of();
        if (entry.get("CVE") != null) {
            CVE = Arrays.stream(entry.get("CVE")
                            .split(","))
                    .toList();
        }


        return new Dependency(entry.get("groupId"), entry.get("artifactId"), entry.get("version"), CVE);
    }
}
