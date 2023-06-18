package cucumber.utils;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.GroupId;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import io.cucumber.datatable.DataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DependencyDatatableConverter {

    public static List<Dependency> getFrom(DataTable dataTable) {
        List<Dependency> dependencies = new ArrayList<>();
        dataTable.entries().forEach(entry -> {
            dependencies.add(getDependency(entry));
        });

        return dependencies;
    }

    private static Dependency getDependency(Map<String, String> entry) {
        return new Dependency(new GroupId(entry.get("groupId")), entry.get("artifactId"), Optional.ofNullable(SemanticVersion.from(entry.get("version"))));
    }
}
