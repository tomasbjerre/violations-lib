package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.reports.Parser.CPPLINT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class CppLintTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsReporterApi() //
            .withPattern(".*/cpplint/.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(CPPLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(3);

    assertThat(actual.get(0).getMessage()) //
        .isEqualTo(
            "No copyright message found.  You should have a line: \"Copyright [year] <Copyright Owner>\"");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("cpp/test.cpp");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actual.get(0).getRule().get()) //
        .isEqualTo("legal/copyright");
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(0);
    assertThat(actual.get(0).getEndLine()) //
        .isEqualTo(0);

    assertThat(actual.get(2).getMessage()) //
        .isEqualTo("Missing space before ( in while(");
    assertThat(actual.get(2).getFile()) //
        .isEqualTo("cpp/test.cpp");
    assertThat(actual.get(2).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actual.get(2).getRule().get()) //
        .isEqualTo("whitespace/parens");
    assertThat(actual.get(2).getStartLine()) //
        .isEqualTo(11);
    assertThat(actual.get(2).getEndLine()) //
        .isEqualTo(11);
  }
}
