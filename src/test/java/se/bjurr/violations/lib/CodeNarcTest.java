package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.CODENARC;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class CodeNarcTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/codenarc/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CODENARC) //
            .violations();

    assertThat(actual) //
        .hasSize(32);

    assertThat(actual.get(0).getMessage()) //
        .isEqualTo("In most cases, exceptions should not be caught and ignored (swallowed).");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("foo/bar/Test.groovy");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(WARN);
    assertThat(actual.get(0).getRule()) //
        .isEqualTo("EmptyCatchBlock");
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(192);
    assertThat(actual.get(0).getEndLine()) //
        .isEqualTo(192);

    assertThat(actual.get(2).getMessage()) //
        .isEqualTo(
            "Catching Exception is often too broad or general. It should usually be restricted to framework or infrastructure code, rather than application code.");
  }
}
