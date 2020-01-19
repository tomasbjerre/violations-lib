package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.MYPY;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

/** Created by matthew on 27/04/16. */
public class MyPyTest {

  @Test
  public void testThatViolationsCanBeParsed() {

    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsApi() //
            .withPattern(".*/mypy/.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(MYPY) //
            .violations();

    assertThat(actual) //
        .hasSize(5);

    assertThat(actual.get(3)) //
        .isEqualTo( //
            violationBuilder() //
                .setParser(MYPY) //
                .setFile("fs/cs/backend/log.py") //
                .setStartLine(16) //
                .setMessage("\"LogRecord\" has no attribute \"user_uuid\"") //
                .setSeverity(ERROR) //
                .build() //
            );

    assertThat(actual.get(2)) //
        .isEqualTo( //
            violationBuilder() //
                .setParser(MYPY) //
                .setFile("fs/cs/backend/log.py") //
                .setStartLine(17) //
                .setMessage("\"LogRecord\" has no attribute \"tenant_id\"") //
                .setSeverity(ERROR) //
                .build() //
            );
  }
}
