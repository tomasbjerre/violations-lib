package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.PYDOCSTYLE;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

/** Created by matthew on 27/04/16. */
public class PyDocStyleTest {

  @Test
  public void testThatViolationsCanBeParsed() {

    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/pydocstyle/pydocstyle\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(PYDOCSTYLE) //
            .violations();

    assertThat(actual) //
        .hasSize(33);

    assertThat(actual.get(0)) //
        .isEqualTo( //
            violationBuilder() //
                .setParser(PYDOCSTYLE) //
                .setFile("fs/csm/admin_api/ui_api.py") //
                .setStartLine(1) //
                .setRule("D100") //
                .setMessage("Missing docstring in public module") //
                .setSeverity(ERROR) //
                .build() //
            );

    assertThat(actual.get(1)) //
        .isEqualTo( //
            violationBuilder() //
                .setParser(PYDOCSTYLE) //
                .setFile("fs/csm/admin_api/main.py") //
                .setStartLine(1) //
                .setRule("D100") //
                .setMessage("Missing docstring in public module") //
                .setSeverity(ERROR) //
                .build() //
            );

    assertThat(actual.get(20)) //
        .isEqualTo( //
            violationBuilder() //
                .setParser(PYDOCSTYLE) //
                .setFile("fs/csm/admin_api/auth.py") //
                .setStartLine(73) //
                .setRule("D101") //
                .setMessage("Missing docstring in public class") //
                .setSeverity(ERROR) //
                .build() //
            );
  }

  @Test
  public void testThatViolationsCanBeParsedWithoutS() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/pydocstyle/pydocstyle-without-s\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(PYDOCSTYLE) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    assertThat(actual.get(0).getMessage()) //
        .isEqualTo("Missing docstring in public module");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("test.py");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actual.get(0).getRule()) //
        .isEqualTo("D100");
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(1);
    assertThat(actual.get(0).getEndLine()) //
        .isEqualTo(1);

    assertThat(actual.get(1).getMessage()) //
        .isEqualTo("Missing docstring in public function");
  }

  @Test
  public void testThatViolationsCanBeParsedWithS() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/pydocstyle/pydocstyle-with-s\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(PYDOCSTYLE) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    assertThat(actual.get(0).getMessage()) //
        .isEqualTo("Missing docstring in public module");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("test.py");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actual.get(0).getRule()) //
        .isEqualTo("D100");
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(1);
    assertThat(actual.get(0).getEndLine()) //
        .isEqualTo(1);

    assertThat(actual.get(1).getMessage()) //
        .isEqualTo("Missing docstring in public function");
    assertThat(actual.get(1).getFile()) //
        .isEqualTo("test.py");
    assertThat(actual.get(1).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actual.get(1).getRule()) //
        .isEqualTo("D103");
    assertThat(actual.get(1).getStartLine()) //
        .isEqualTo(1);
    assertThat(actual.get(1).getEndLine()) //
        .isEqualTo(1);
  }
}
