package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Parser.SIMIAN;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class SimianTest {
  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/simian/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(SIMIAN) //
            .violations();

    assertThat(actual) //
        .hasSize(8);

    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .startsWith("Duplication");
    assertThat(new ArrayList<>(actual).get(0).getFile()) //
        .isEqualTo("c:/java/foo1.java");
    assertThat(new ArrayList<>(actual).get(0).getSeverity()) //
        .isEqualTo(INFO);
    assertThat(new ArrayList<>(actual).get(0).getRule()) //
        .isEqualTo("DUPLICATION");
    assertThat(new ArrayList<>(actual).get(0).getStartLine()) //
        .isEqualTo(11);
    assertThat(new ArrayList<>(actual).get(0).getEndLine()) //
        .isEqualTo(16);

    assertThat(new ArrayList<>(actual).get(5).getMessage()) //
        .startsWith("Duplication");
  }
}
