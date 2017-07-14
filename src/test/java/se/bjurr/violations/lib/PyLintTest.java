package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Parser.PYLINT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class PyLintTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsReporterApi() //
            .withPattern(".*/pylint/.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(PYLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(136);

    assertThat(actual.get(0).getFile()) //
        .isEqualTo("marshmallow/schema.py");
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(190);
    assertThat(actual.get(0).getMessage()) //
        .isEqualTo("Wrong continued indentation (add 1 space).");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(INFO);
    assertThat(actual.get(0).getRule().orNull()) //
        .isEqualTo("C0330(bad-continuation)");
  }
}
