package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Parser.SONAR;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class SonarTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/sonar/.*\\.json$") //
            .inFolder(rootFolder) //
            .findAll(SONAR) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation actualViolationZero = actual.get(0);
    assertThat(actualViolationZero.getFile()) //
        .isEqualTo("src/main/java/com/example/myapp/App.java");
    assertThat(actualViolationZero.getStartLine()) //
        .isEqualTo(28);
    assertThat(actualViolationZero.getMessage()) //
        .startsWith("Complete the");
    assertThat(actualViolationZero.getReporter()) //
        .isEqualTo(SONAR.name());
    assertThat(actualViolationZero.getRule()) //
        .isEqualTo("squid:S1135");
    assertThat(actualViolationZero.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(actualViolationZero.getSource()) //
        .isEqualTo("com.example:myapp:src/main/java/com/example/myapp/App.java");
  }
}
