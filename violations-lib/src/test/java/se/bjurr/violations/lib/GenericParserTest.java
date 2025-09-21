package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.GENERIC;

import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class GenericParserTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi()
            .withPattern(".*/file-parser/file.*\\.txt$")
            .inFolder(rootFolder) //
            .findAll(GENERIC)
            .violations();
    assertThat(actual) //
        .contains( //
            violationBuilder() //
                .setParser(GENERIC) //
                .setFile("Generic Comment") //
                .setStartLine(0)
                .setColumn(0)
                .setMessage("This is just a dummy text.") //
                .setSeverity(INFO) //
                .build() //
            ) //
        .hasSize(1);
  }
}
