package cucumber.mock;

import com.globaldashboard.dependencies.domain.ProjectInformation;
import com.globaldashboard.dependencies.domain.port.secondary.PomHttpRetriever;
import com.globaldashboard.dependencies.infrastructure.primary.exception.InvalidPomException;
import com.globaldashboard.dependencies.infrastructure.secondary.PomFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MockPomHttpRetriever implements PomHttpRetriever {


    public static final String HTTP_PREFIX = "http://";
    public static final String HTTPS_PREFIX = "https://";
    public static final String FIXTURES_FOLDER = "src/test/java/com/globaldashboard/fixture/";

    @Override
    public ProjectInformation getFromURL(String url) {
        PomFactory pomFactory = new PomFactory();
        Document pomXML;
        String filePath = url
                .replace(HTTP_PREFIX, "")
                .replace(HTTPS_PREFIX, "");
        try {
            pomXML = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new FileInputStream(FIXTURES_FOLDER +filePath));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new InvalidPomException(e);
        }

        return pomFactory.getPomFrom(Map.of("", pomXML));
    }

    @Override
    public Set<ProjectInformation> getFromURLs(Set<String> pomURLs) {
        return pomURLs.stream()
                .map(this::getFromURL)
                .collect(Collectors.toSet());
    }
}
