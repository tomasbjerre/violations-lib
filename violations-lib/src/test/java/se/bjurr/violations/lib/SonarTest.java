package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Parser.SONAR;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class SonarTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/sonar/sonar-report\\.json$") //
            .inFolder(rootFolder) //
            .findAll(SONAR) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation actualViolationZero = new ArrayList<>(actual).get(0);
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

  @Test
  public void testThatViolationsCanBeParsed2() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/sonar/sonar-report-2\\.json$") //
            .inFolder(rootFolder) //
            .findAll(SONAR) //
            .violations();

    assertThat(actual) //
        .hasSize(88);

    final Violation actualViolationZero = new ArrayList<>(actual).get(0);
    assertThat(actualViolationZero.getFile()) //
        .isEqualTo("app/controllers/API.java");
    assertThat(actualViolationZero.getStartLine()) //
        .isEqualTo(340);
    assertThat(actualViolationZero.getColumn()) //
        .isEqualTo(26);
    assertThat(actualViolationZero.getEndLine()) //
        .isEqualTo(340);
    assertThat(actualViolationZero.getEndColumn()) //
        .isEqualTo(51);
    assertThat(actualViolationZero.getMessage()) //
        .startsWith("Use try-with-resources or close this");
    assertThat(actualViolationZero.getReporter()) //
        .isEqualTo(SONAR.name());
    assertThat(actualViolationZero.getRule()) //
        .isEqualTo("squid:S2095");
    assertThat(actualViolationZero.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actualViolationZero.getSource()) //
        .isEqualTo("pki-middleware-sonar:app/controllers/API.java");
  }

  @Test
  public void testThatViolationsCanBeParsedWithIssuesReportVersion7_5() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/sonar/sonar-report-3\\.json$") //
            .inFolder(rootFolder) //
            .findAll(SONAR) //
            .violations();

    assertThat(actual) //
        .hasSize(5);

    final Violation actualViolationZero = new ArrayList<>(actual).get(4);
    assertThat(actualViolationZero.getFile()) //
        .isEqualTo("src/main/java/com/example/component/application/providers/WebProvider.java");
    assertThat(actualViolationZero.getStartLine()) //
        .isEqualTo(56);
    assertThat(actualViolationZero.getMessage()) //
        .isEqualTo("Complete the task associated to this TODO comment.");

    final Violation actualViolationFour = new ArrayList<>(actual).get(0);
    assertThat(actualViolationFour.getMessage()) //
        .isEqualTo(
            "'PASSWORD' detected in this expression, review this potentially hard-coded credential.");
  }
}
