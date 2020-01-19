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
                .setFile("fs/csm/admin_api/__init__.py") //
                .setStartLine(1) //
                .setRule("D104") //
                .setMessage("Missing docstring in public package") //
                .setSeverity(ERROR) //
                .build() //
            );

    assertThat(actual.get(1)) //
        .isEqualTo( //
            violationBuilder() //
                .setParser(PYDOCSTYLE) //
                .setFile("fs/csm/admin_api/admin_api.py") //
                .setStartLine(614) //
                .setRule("D101") //
                .setMessage("Missing docstring in public class") //
                .setSeverity(ERROR) //
                .build() //
            );

    assertThat(actual.get(20)) //
        .isEqualTo( //
            violationBuilder() //
                .setParser(PYDOCSTYLE) //
                .setFile("fs/csm/admin_api/dao.py") //
                .setStartLine(1) //
                .setRule("D100") //
                .setMessage("Missing docstring in public module") //
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

    Violation violation = actual.get(1);
    assertThat(violation.getMessage()) //
        .isEqualTo("Missing docstring in public module");
    assertThat(violation.getFile()) //
        .isEqualTo("test.py");
    assertThat(violation.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation.getRule()) //
        .isEqualTo("D100");
    assertThat(violation.getStartLine()) //
        .isEqualTo(1);
    assertThat(violation.getEndLine()) //
        .isEqualTo(1);

    assertThat(actual.get(0).getMessage()) //
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

    Violation violation1 = actual.get(1);
    assertThat(violation1.getMessage()) //
        .isEqualTo("Missing docstring in public module");
    assertThat(violation1.getFile()) //
        .isEqualTo("test.py");
    assertThat(violation1.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation1.getRule()) //
        .isEqualTo("D100");
    assertThat(violation1.getStartLine()) //
        .isEqualTo(1);
    assertThat(violation1.getEndLine()) //
        .isEqualTo(1);

    Violation violation0 = actual.get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("Missing docstring in public function");
    assertThat(violation0.getFile()) //
        .isEqualTo("test.py");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation0.getRule()) //
        .isEqualTo("D103");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(1);
    assertThat(violation0.getEndLine()) //
        .isEqualTo(1);
  }
}
