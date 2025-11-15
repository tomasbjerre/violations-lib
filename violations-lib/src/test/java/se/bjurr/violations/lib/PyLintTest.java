package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Parser.PYLINT;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class PyLintTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/pylint/.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(PYLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(135);

    Violation violation = new ArrayList<>(actual).get(0);
    assertThat(violation.getFile()) //
        .isEqualTo("marshmallow/class_registry.py");
    assertThat(violation.getStartLine()) //
        .isEqualTo(73);
    assertThat(violation.getMessage()) //
        .isEqualTo("Wrong continued indentation (add 16 spaces).");
    assertThat(violation.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(violation.getRule()) //
        .isEqualTo("C0330(bad-continuation)");
  }
}
