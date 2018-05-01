package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.FLAKE8;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class Flake8Test {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/flake8/.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(FLAKE8) //
            .violations();

    assertThat(actual) //
        .contains( //
            violationBuilder() //
                .setParser(FLAKE8) //
                .setFile("myproject/__init__.py") //
                .setStartLine(7) //
                .setEndLine(7) //
                .setMessage("'db' imported but unused") //
                .setRule("401") //
                .setSeverity(ERROR) //
                .build() //
            ) //
        .hasSize(17);

    assertThat(actual.get(9).getMessage()) //
        .isEqualTo("test with F");
    assertThat(actual.get(9).getFile()) //
        .isEqualTo("__fake__.py");
    assertThat(actual.get(9).getSeverity()) //
        .isEqualTo(ERROR);

    assertThat(actual.get(10).getMessage()) //
        .isEqualTo("test with W");
    assertThat(actual.get(10).getFile()) //
        .isEqualTo("__fake__.py");
    assertThat(actual.get(10).getSeverity()) //
        .isEqualTo(WARN);

    assertThat(actual.get(11).getMessage()) //
        .isEqualTo("test with C");
    assertThat(actual.get(11).getFile()) //
        .isEqualTo("__fake__.py");
    assertThat(actual.get(11).getSeverity()) //
        .isEqualTo(INFO);

    assertThat(actual.get(16).getMessage()) //
        .isEqualTo("expected 2 blank lines, found 1");
    assertThat(actual.get(16).getFile()) //
        .isEqualTo("python/project/file.py");
    assertThat(actual.get(16).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actual.get(16).getRule()) //
        .isEqualTo("302");
    assertThat(actual.get(16).getColumn()) //
        .isEqualTo(1);
    assertThat(actual.get(16).getStartLine()) //
        .isEqualTo(57);
    assertThat(actual.get(16).getEndLine()) //
        .isEqualTo(57);
  }
}
