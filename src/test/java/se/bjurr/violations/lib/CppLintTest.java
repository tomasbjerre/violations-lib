package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.CPPLINT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class CppLintTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
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
    assertThat(actual.get(0).getRule()) //
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
    assertThat(actual.get(2).getRule()) //
        .isEqualTo("whitespace/parens");
    assertThat(actual.get(2).getStartLine()) //
        .isEqualTo(11);
    assertThat(actual.get(2).getEndLine()) //
        .isEqualTo(11);
  }

  @Test
  public void testThatViolationsCanBeParsed2() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*cpplint-result\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation violation = actual.get(0);
    assertThat(violation.getMessage()) //
        .isEqualTo("Using C-style cast.  Use reinterpret_cast<uint8_t *>(...) instead");
    assertThat(violation.getFile()) //
        .isEqualTo("pump/src/hal/stm32f4xx/devices/spi/spi_unit0_com.c");
    assertThat(violation.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation.getRule()) //
        .isEqualTo("readability/casting");
    assertThat(violation.getStartLine()) //
        .isEqualTo(737);
    assertThat(violation.getEndLine()) //
        .isEqualTo(737);
  }
}
