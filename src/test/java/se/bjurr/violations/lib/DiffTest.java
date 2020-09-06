package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.reports.Parser.DIFF;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class DiffTest {

  @Test
  public void testChange() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/diff/0001-.*") //
            .inFolder(rootFolder) //
            .findAll(DIFF) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    Violation v0 = new ArrayList<>(actual).get(0);
    assertThat(v0.getMessage()) //
        .contains("Changelog of Violations lib");
    assertThat(v0.getFile()) //
        .isEqualTo("CHANGELOG.md");
    assertThat(v0.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(v0.getStartLine()) //
        .isEqualTo(2);

    Violation v1 = new ArrayList<>(actual).get(1);
    assertThat(v1.getMessage()) //
        .contains("version = 1.127");
    assertThat(v1.getFile()) //
        .isEqualTo("gradle.properties");
    assertThat(v1.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(v1.getStartLine()) //
        .isEqualTo(0);
  }
}
