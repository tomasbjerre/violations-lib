package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Reporter.PYDOCSTYLE;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

/** Created by matthew on 27/04/16. */
public class PyDocStyleTest {

  @Test
  public void testThatViolationsCanBeParsed() {

    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsReporterApi() //
            .withPattern(".*/pydocstyle/.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(PYDOCSTYLE) //
            .violations();

    assertThat(actual) //
        .hasSize(33);

    assertThat(actual.get(0)) //
        .isEqualTo( //
            violationBuilder() //
                .setReporter(PYDOCSTYLE) //
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
                .setReporter(PYDOCSTYLE) //
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
                .setReporter(PYDOCSTYLE) //
                .setFile("fs/csm/admin_api/auth.py") //
                .setStartLine(73) //
                .setRule("D101") //
                .setMessage("Missing docstring in public class") //
                .setSeverity(ERROR) //
                .build() //
            );
  }
}
