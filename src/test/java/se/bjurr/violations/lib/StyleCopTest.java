package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Parser.STYLECOP;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class StyleCopTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/stylecop/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(STYLECOP) //
            .violations();

    assertThat(actual) //
        .hasSize(53);

    final Violation actualViolationZero = actual.get(0);
    assertThat(actualViolationZero.getFile()) //
        .isEqualTo("E:/Jenkins/jobs/Tools Development/workspace/Tools/Tools.SSOTester/Form1.cs");
    assertThat(actualViolationZero.getStartLine()) //
        .isEqualTo(353);
    assertThat(actualViolationZero.getMessage()) //
        .startsWith("A closing");
    assertThat(actualViolationZero.getReporter()) //
        .isEqualTo(STYLECOP.name());
    assertThat(actualViolationZero.getRule()) //
        .isEqualTo("ClosingCurlyBracketsMustNotBePrecededByBlankLine");
    assertThat(actualViolationZero.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(actualViolationZero.getSource()) //
        .isEqualTo("E:/Jenkins/jobs/Tools Development/workspace/Tools/Tools.SSOTester/Form1.cs");

    final Violation actualViolationOne = actual.get(1);
    assertThat(actualViolationOne.getFile()) //
        .isEqualTo("E:/Jenkins/jobs/Tools Development/workspace/Tools/Tools.SSOTester/Form1.cs");
    assertThat(actualViolationOne.getStartLine()) //
        .isEqualTo(348);
    assertThat(actualViolationOne.getMessage()) //
        .startsWith("The body");
    assertThat(actualViolationOne.getReporter()) //
        .isEqualTo(STYLECOP.name());
    assertThat(actualViolationOne.getRule()) //
        .isEqualTo("CurlyBracketsMustNotBeOmitted");
    assertThat(actualViolationOne.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(actualViolationOne.getSource()) //
        .isEqualTo("E:/Jenkins/jobs/Tools Development/workspace/Tools/Tools.SSOTester/Form1.cs");
  }
}
