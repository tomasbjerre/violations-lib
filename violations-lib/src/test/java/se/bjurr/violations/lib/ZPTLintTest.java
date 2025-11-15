package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.reports.Parser.ZPTLINT;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class ZPTLintTest {
  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/zptlint/.*\\.log$") //
            .inFolder(rootFolder) //
            .findAll(ZPTLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    assertThat(new ArrayList<>(actual).get(1).getMessage()) //
        .isEqualTo("abc def ghe '\" 123");
    assertThat(new ArrayList<>(actual).get(1).getFile()) //
        .isEqualTo("cpplint.py");
    assertThat(new ArrayList<>(actual).get(1).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(new ArrayList<>(actual).get(1).getRule()) //
        .isEqualTo("ZPT");
    assertThat(new ArrayList<>(actual).get(1).getStartLine()) //
        .isEqualTo(4796);
    assertThat(new ArrayList<>(actual).get(1).getEndLine()) //
        .isEqualTo(4796);

    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .isEqualTo("abc '\" 123 def ghe");
  }
}
