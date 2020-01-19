package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.reports.Parser.ZPTLINT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class ZPTLintTest {
  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/zptlint/.*\\.log$") //
            .inFolder(rootFolder) //
            .findAll(ZPTLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    assertThat(actual.get(1).getMessage()) //
        .isEqualTo("abc def ghe '\" 123");
    assertThat(actual.get(1).getFile()) //
        .isEqualTo("cpplint.py");
    assertThat(actual.get(1).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actual.get(1).getRule()) //
        .isEqualTo("ZPT");
    assertThat(actual.get(1).getStartLine()) //
        .isEqualTo(4796);
    assertThat(actual.get(1).getEndLine()) //
        .isEqualTo(4796);

    assertThat(actual.get(0).getMessage()) //
        .isEqualTo("abc '\" 123 def ghe");
  }
}
