package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
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
        .isEqualTo("missing starting space in comment");
    assertThat(violation0.getFile()) //
        .isEqualTo("file.yml");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("comments");

    final Violation violation1 = actual.get(1);
    assertThat(violation1.getMessage()) //
        .isEqualTo("trailing spaces");
    assertThat(violation1.getFile()) //
        .isEqualTo("test/file.yml");
    assertThat(violation1.getSeverity()) //
        .isEqualTo(ERROR);

    final Violation violation2 = actual.get(3);
    assertThat(violation2.getMessage()) //
        .isEqualTo("syntax error: expected '<document start>', but found '<block mapping start>'");
    assertThat(violation2.getFile()) //
        .isEqualTo("./molecule-default/create.yml");
    assertThat(violation2.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation2.getRule()).isEmpty();

    assertThat(actual) //
        .hasSize(4);
  }
}
