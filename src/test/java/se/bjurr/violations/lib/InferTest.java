package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.reports.Reporter.INFER;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class InferTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsReporterApi() //
            .withPattern(".*/infer/.*\\.csv$") //
            .inFolder(rootFolder) //
            .findAll(INFER) //
            .violations();

    assertThat(actual) //
        .hasSize(3);

    Violation violationOne = actual.get(0);
    assertThat(violationOne.getMessage()) //
        .isEqualTo(
            "object `s` last assigned on line 28 could be null and is dereferenced at line 29");
    assertThat(violationOne.getFile()) //
        .isEqualTo("app/src/main/java/infer/inferandroidexample/MainActivity.java");
    assertThat(violationOne.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violationOne.getRule().get()) //
        .isEqualTo("NULL_DEREFERENCE");
    assertThat(violationOne.getStartLine()) //
        .isEqualTo(29);
    assertThat(violationOne.getEndLine()) //
        .isEqualTo(29);

    assertThat(actual.get(2).getMessage()) //
        .isEqualTo("object returned by `source()` could be null and is dereferenced at line 24");
  }
}
