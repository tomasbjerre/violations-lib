package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.JSLINT;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class JSHintTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/jshint/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JSLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(6);

    Violation violation = new ArrayList<>(actual).get(2);
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
