package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.CLANG;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class BanditTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/bandit/bandit\\.out$") //
            .inFolder(rootFolder) //
            .findAll(CLANG) //
            .violations();

    assertThat(actual) //
        .hasSize(3);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo(
            "B101: Use of assert detected. The enclosed code will be removed when compiling to optimised byte code.");
    assertThat(violation0.getFile()) //
        .isEqualTo("/home/bjerre/workspace/bandit/examples/assert.py");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(SEVERITY.INFO);
    assertThat(violation0.getRule()) //
        .isEqualTo("");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(1);

    final Violation violation1 = new ArrayList<>(actual).get(1);
    assertThat(violation1.getSeverity()) //
        .isEqualTo(WARN);
  }
}
