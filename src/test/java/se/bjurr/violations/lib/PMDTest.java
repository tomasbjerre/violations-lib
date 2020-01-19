package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.PMD;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class PMDTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/pmd/main\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(PMD) //
            .violations();

    assertThat(actual) //
        .hasSize(4);

    final Violation violationZero = actual.get(0);
    assertThat(violationZero.getFile()) //
        .isEqualTo("/src/main/java/se/bjurr/violations/lib/example/MyClass.java");
    assertThat(violationZero.getMessage()) //
        .startsWith("Ensure") //
        .doesNotContain("CDATA");
    assertThat(violationZero.getStartLine()) //
        .isEqualTo(16);
    assertThat(violationZero.getEndLine()) //
        .isEqualTo(16);
    assertThat(violationZero.getRule()) //
        .isEqualTo("OverrideBothEqualsAndHashcode");
    assertThat(violationZero.getSeverity()) //
        .isEqualTo(WARN);
  }

  @Test
  public void testThatViolationsCanBeParsedCsvDelta() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/pmd/csv-delta\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(PMD) //
            .violations();

    assertThat(actual) //
        .hasSize(5);

    final Violation violationZero = actual.get(0);
    assertThat(violationZero.getFile()) //
        .isEqualTo("src/CustomTableClass.java");
    assertThat(violationZero.getMessage()) //
        .startsWith("Description") //
        .doesNotContain("CDATA");
    assertThat(violationZero.getStartLine()) //
        .isEqualTo(39);
    assertThat(violationZero.getEndLine()) //
        .isEqualTo(39);
    assertThat(violationZero.getRule()) //
        .isEqualTo("RULE5");
    assertThat(violationZero.getSeverity()) //
        .isEqualTo(WARN);
  }

  @Test
  public void testThatViolationsCanBeParsedIfNoRuleset() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/pmd/no-ruleset\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(PMD) //
            .violations();

    assertThat(actual) //
        .hasSize(3);

    final Violation violationZero = actual.get(0);
    assertThat(violationZero.getFile()) //
        .isEqualTo(
            "/home/cm/prod/workspace/applikation-mr-pipeline@3/applikation-web/src/main/java/pkg/applikation/application/Some.java");
    assertThat(violationZero.getMessage()) //
        .isEqualTo("Applikationslagret f&#xe5;r inte kommunicera upp&#xe5;t.") //
        .doesNotContain("CDATA");
    assertThat(violationZero.getStartLine()) //
        .isEqualTo(1);
    assertThat(violationZero.getEndLine()) //
        .isEqualTo(149);
    assertThat(violationZero.getRule()) //
        .isEqualTo("ApplicationAccessLimit");
    assertThat(violationZero.getSeverity()) //
        .isEqualTo(ERROR);
  }

  @Test
  public void testThatPHPMDViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/pmd/phpmd\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(PMD) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violationZero = actual.get(0);
    assertThat(violationZero.getFile()) //
        .isEqualTo("/home/bjerre/workspace/pull-request-notifier-for-stash/api.php");
    assertThat(violationZero.getMessage()) //
        .startsWith("Avoid unused");
  }
}
