package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Parser.PERLCRITIC;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class PerlCriticTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/perlcritic/.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(PERLCRITIC) //
            .violations();

    assertThat(actual) //
        .hasSize(6);

    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .isEqualTo("Return value of flagged function ignored - print");
    assertThat(new ArrayList<>(actual).get(0).getFile()) //
        .isEqualTo("perl/example.pl");
    assertThat(new ArrayList<>(actual).get(0).getSeverity()) //
        .isEqualTo(INFO);
    assertThat(new ArrayList<>(actual).get(0).getRule()) //
        .isEqualTo("See pages 208,278 of PBP.");
    assertThat(new ArrayList<>(actual).get(0).getStartLine()) //
        .isEqualTo(8);
    assertThat(new ArrayList<>(actual).get(0).getEndLine()) //
        .isEqualTo(8);

    assertThat(new ArrayList<>(actual).get(1).getMessage()) //
        .isEqualTo("Postfix control \"if\" used");
    assertThat(new ArrayList<>(actual).get(1).getFile()) //
        .isEqualTo("perl/example.pl");
    assertThat(new ArrayList<>(actual).get(1).getSeverity()) //
        .isEqualTo(INFO);
    assertThat(new ArrayList<>(actual).get(1).getRule()) //
        .isEqualTo("See pages 93,94 of PBP.");
    assertThat(new ArrayList<>(actual).get(1).getStartLine()) //
        .isEqualTo(7);
    assertThat(new ArrayList<>(actual).get(1).getEndLine()) //
        .isEqualTo(7);

    assertThat(new ArrayList<>(actual).get(5).getSeverity()) //
        .isEqualTo(INFO);
  }
}
