package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Parser.PERLCRITIC;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class PerlCriticTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/perlcritic/.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(PERLCRITIC) //
            .violations();

    assertThat(actual) //
        .hasSize(6);

    assertThat(actual.get(0).getMessage()) //
        .isEqualTo("Code is not tidy");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("perl/example.pl");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(INFO);
    assertThat(actual.get(0).getRule()) //
        .isEqualTo("See page 33 of PBP.");
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(1);
    assertThat(actual.get(0).getEndLine()) //
        .isEqualTo(1);

    assertThat(actual.get(1).getMessage()) //
        .isEqualTo("Code not contained in explicit package");
    assertThat(actual.get(1).getFile()) //
        .isEqualTo("perl/example.pl");
    assertThat(actual.get(1).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actual.get(1).getRule()) //
        .isEqualTo("Violates encapsulation.");
    assertThat(actual.get(1).getStartLine()) //
        .isEqualTo(1);
    assertThat(actual.get(1).getEndLine()) //
        .isEqualTo(1);

    assertThat(actual.get(5).getSeverity()) //
        .isEqualTo(INFO);
  }
}
