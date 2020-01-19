package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;

public class CodeClimateTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/codeclimate/example\\.json$") //
            .inFolder(rootFolder) //
            .findAll(Parser.CODECLIMATE) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violation0 = actual.get(1);
    assertThat(violation0.getMessage()) //
        .isEqualTo("Method `destroy` has 6 arguments (exceeds 4 allowed). Consider refactoring.");
    assertThat(violation0.getFile()) //
        .isEqualTo("argument_count.rb");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(2);
  }
}
