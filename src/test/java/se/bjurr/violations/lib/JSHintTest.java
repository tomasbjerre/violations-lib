package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.JSLINT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class JSHintTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsApi() //
            .withPattern(".*/jshint/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JSLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(6);

    Violation violation = actual.get(2);
    assertThat(violation.getFile()) //
        .isEqualTo("../../../web/js-file.js");
    assertThat(violation.getMessage()) //
        .startsWith("Use") //
        .doesNotContain("CDATA");
    assertThat(violation.getStartLine()) //
        .isEqualTo(4);
    assertThat(violation.getEndLine()) //
        .isEqualTo(4);
    assertThat(violation.getSeverity()) //
        .isEqualTo(WARN);
  }
}
