package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Parser.SIMIAN;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class SimianTest {
  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/simian/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(SIMIAN) //
            .violations();

    assertThat(actual) //
        .hasSize(12);

    assertThat(actual.get(0).getMessage()) //
        .startsWith("Duplication");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("c:/java/foo1.java");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(INFO);
    assertThat(actual.get(0).getRule()) //
        .isEqualTo("DUPLICATION");
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(11);
    assertThat(actual.get(0).getEndLine()) //
        .isEqualTo(16);

    assertThat(actual.get(5).getMessage()) //
        .startsWith("Duplication");
  }
}
