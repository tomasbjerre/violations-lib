package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.reports.Parser.YAMLLINT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class YAMLlintTest {
  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/yamllint/.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(YAMLLINT) //
            .violations();

    final Violation violation0 = actual.get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("syntax error: expected '<document start>', but found '<block mapping start>'");
    assertThat(violation0.getFile()) //
        .isEqualTo("./molecule-default/create.yml");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation0.getRule()) //
        .isEqualTo("");

    assertThat(actual) //
        .hasSize(4);
  }
}
