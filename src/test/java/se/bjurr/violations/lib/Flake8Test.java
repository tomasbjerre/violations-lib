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
                .setRule("F401") //
                .setSeverity(ERROR) //
                .build() //
            ) //
        .hasSize(17);

    Violation violation9 = actual.get(9);
    assertThat(violation9.getMessage()) //
        .isEqualTo("test with F");
    assertThat(violation9.getFile()) //
        .isEqualTo("__fake__.py");
    assertThat(violation9.getRule()) //
        .isEqualTo("F265");
    assertThat(violation9.getSeverity()) //
        .isEqualTo(ERROR);

    Violation violation10 = actual.get(10);
    assertThat(violation10.getMessage()) //
        .isEqualTo("test with W");
    assertThat(violation10.getFile()) //
        .isEqualTo("__fake__.py");
    assertThat(violation10.getSeverity()) //
        .isEqualTo(WARN);

    Violation violation11 = actual.get(11);
    assertThat(violation11.getMessage()) //
        .isEqualTo("test with C");
    assertThat(violation11.getFile()) //
        .isEqualTo("__fake__.py");
    assertThat(violation11.getSeverity()) //
        .isEqualTo(INFO);

    Violation violation16 = actual.get(16);
    assertThat(violation16.getMessage()) //
        .isEqualTo("expected 2 blank lines, found 1");
    assertThat(violation16.getFile()) //
        .isEqualTo("python/project/file.py");
    assertThat(violation16.getRule()) //
        .isEqualTo("E302");
    assertThat(violation16.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation16.getRule()) //
        .isEqualTo("E302");
    assertThat(violation16.getColumn()) //
        .isEqualTo(1);
    assertThat(violation16.getStartLine()) //
        .isEqualTo(57);
    assertThat(violation16.getEndLine()) //
        .isEqualTo(57);
  }

  @Test
  public void testThatViolationsCanBeParsedWithAnsibleLint() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/ansiblelint/ansiblelint\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(FLAKE8) //
            .violations();

    assertThat(actual) //
        .hasSize(8);

    Violation violation0 = actual.get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("Commands should not change things if nothing needs doing");
    assertThat(violation0.getFile()) //
        .isEqualTo("Weblogic/playbooks/getDSdetails.yml");
    assertThat(violation0.getRule()) //
        .isEqualTo("EANSIBLE0012");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation0.getStartLine()) //
        .isEqualTo(25);

    Violation violation7 = actual.get(7);
    assertThat(violation7.getMessage()) //
        .isEqualTo("This line is just added to test W");
    assertThat(violation7.getRule()) //
        .isEqualTo("WANSIBLE0012");
    assertThat(violation7.getSeverity()) //
        .isEqualTo(WARN);
  }
}
