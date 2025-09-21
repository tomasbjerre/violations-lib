package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.CPPLINT;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class CppLintTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/cpplint/cpplint\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(CPPLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(3);

    Violation violation = new ArrayList<>(actual).get(2);
    assertThat(violation.getMessage()) //
        .isEqualTo(
            "No copyright message found.  You should have a line: \"Copyright [year] <Copyright Owner>\"");
    assertThat(violation.getFile()) //
        .isEqualTo("cpp/test.cpp");
    assertThat(violation.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation.getRule()) //
        .isEqualTo("legal/copyright");
    assertThat(violation.getStartLine()) //
        .isEqualTo(0);
    assertThat(violation.getEndLine()) //
        .isEqualTo(0);

    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .isEqualTo("Missing space before ( in while(");
    assertThat(new ArrayList<>(actual).get(0).getFile()) //
        .isEqualTo("cpp/test.cpp");
    assertThat(new ArrayList<>(actual).get(0).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(new ArrayList<>(actual).get(0).getRule()) //
        .isEqualTo("whitespace/parens");
    assertThat(new ArrayList<>(actual).get(0).getStartLine()) //
        .isEqualTo(11);
    assertThat(new ArrayList<>(actual).get(0).getEndLine()) //
        .isEqualTo(11);
  }

  @Test
  public void testThatViolationsCanBeParsed2() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*cpplint-result\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation violation = new ArrayList<>(actual).get(0);
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

  @Test
  public void testThatViolationsCanBeParsedMulti() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*cpplint-multi\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(CPPLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(3);

    final Violation violation = new ArrayList<>(actual).get(2);
    assertThat(violation.getMessage()) //
        .isEqualTo(
            "No copyright message found.  You should have a line: \"Copyright [year] <Copyright Owner>\"");
    assertThat(violation.getFile()) //
        .isEqualTo("cpp/test.cpp");
    assertThat(violation.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation.getRule()) //
        .isEqualTo("legal/copyright");
    assertThat(violation.getStartLine()) //
        .isEqualTo(0);
    assertThat(violation.getEndLine()) //
        .isEqualTo(0);

    final Violation violation1 = new ArrayList<>(actual).get(1);
    assertThat(violation1.getStartLine()) //
        .isEqualTo(5);
    assertThat(violation1.getEndLine()) //
        .isEqualTo(5);
  }

  @Test
  public void testThat() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*cpplint\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(34711);
  }
}
