package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.PITEST;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class PiTestTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/pitest/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(PITEST) //
            .violations();

    assertThat(actual) //
        .hasSize(25);

    assertThat(new ArrayList<>(actual).get(0).getFile()) //
        .isEqualTo("se/bjurr/violations/lib/example/CopyOfMyClass.java");
    assertThat(new ArrayList<>(actual).get(0).getMessage())
        .startsWith(
            "NO_COVERAGE, org.pitest.mutationtest.engine.gregor.mutators.ReturnValsMutator");
    assertThat(new ArrayList<>(actual).get(0).getStartLine()) //
        .isEqualTo(17);
    assertThat(new ArrayList<>(actual).get(0).getEndLine()) //
        .isEqualTo(17);
    assertThat(new ArrayList<>(actual).get(0).getSeverity()) //
        .isEqualTo(WARN);
  }
}
