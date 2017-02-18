package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Reporter.PMD;

import java.util.List;

import org.junit.Test;

import se.bjurr.violations.lib.model.Violation;

public class PMDTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual = violationsReporterApi() //
        .withPattern(".*/pmd/main\\.xml$") //
        .inFolder(rootFolder) //
        .findAll(PMD) //
        .violations();

    assertThat(actual)//
        .hasSize(4);

    assertThat(actual.get(0).getFile())//
        .isEqualTo("/src/main/java/se/bjurr/violations/lib/example/MyClass.java");
    assertThat(actual.get(0).getMessage())//
        .startsWith("Empty Code http://")//
        .doesNotContain("CDATA");
    assertThat(actual.get(0).getStartLine())//
        .isEqualTo(9);
    assertThat(actual.get(0).getEndLine())//
        .isEqualTo(11);
    assertThat(actual.get(0).getRule().get())//
        .isEqualTo("EmptyIfStmt");
    assertThat(actual.get(0).getSeverity())//
        .isEqualTo(WARN);
  }

  @Test
  public void testThatPHPMDViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual = violationsReporterApi() //
        .withPattern(".*/pmd/phpmd\\.xml$") //
        .inFolder(rootFolder) //
        .findAll(PMD) //
        .violations();

    assertThat(actual)//
        .hasSize(2);

    assertThat(actual.get(0).getFile())//
        .isEqualTo("/home/bjerre/workspace/pull-request-notifier-for-stash/api.php");
  }
}
