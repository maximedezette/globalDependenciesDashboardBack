package cucumber.utils;

import com.globaldashboard.dependencies.domain.Dependency;
import com.globaldashboard.dependencies.domain.GroupId;
import com.globaldashboard.dependencies.domain.SemanticVersion;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableTypeRegistry;
import io.cucumber.datatable.DataTableTypeRegistryTableConverter;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DependencyDatatableConverterTest {

    @Test
    void shouldConvertDataTableToDependencies() {
        List<List<String>> entries = List.of(
                List.of("groupId", "artifactId", "version"),
                List.of("io.cucumber", "cucumber-bom", "7.6.0")
        );
        DataTable dataTable = DataTable.create(entries, getDataTableTypeRegistryTableConverter());

        List<Dependency> dependencies = DependencyDatatableConverter.getFrom(dataTable);

        assertThat(dependencies).hasSize(1)
                .containsExactly(new Dependency("io.cucumber", "cucumber-bom", "7.6.0"));
    }

    private static DataTableTypeRegistryTableConverter getDataTableTypeRegistryTableConverter() {
        return new DataTableTypeRegistryTableConverter(new DataTableTypeRegistry(Locale.FRANCE));
    }

}