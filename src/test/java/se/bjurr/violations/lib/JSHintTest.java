package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.JSHINT;

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
            .findAll(JSHINT) //
            .violations();

    assertThat(actual) //
        .hasSize(6);

    assertThat(actual.get(0).getFile()) //
        .isEqualTo("../../../web/js-file.js");
    assertThat(actual.get(0).getMessage()) //
        .startsWith("Use") //
        .doesNotContain("CDATA");
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(4);
    assertThat(actual.get(0).getEndLine()) //
        .isEqualTo(4);
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(WARN);
  }
}
