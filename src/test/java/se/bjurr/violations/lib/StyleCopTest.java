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
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsApi() //
            .withPattern(".*/stylecop/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(STYLECOP) //
            .violations();

    assertThat(actual) //
        .hasSize(53);

    Violation actualViolationZero = actual.get(0);
    assertThat(actualViolationZero.getFile()) //
        .isEqualTo("E:/Jenkins/jobs/Tools Development/workspace/Tools/Tools.SSOTester/Form1.cs");
    assertThat(actualViolationZero.getStartLine()) //
        .isEqualTo(17);
    assertThat(actualViolationZero.getMessage()) //
        .startsWith("The do");
    assertThat(actualViolationZero.getReporter()) //
        .isEqualTo(STYLECOP.name());
    assertThat(actualViolationZero.getRule().orNull()) //
        .isEqualTo("ElementDocumentationMustNotHaveDefaultSummary");
    assertThat(actualViolationZero.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(actualViolationZero.getSource().orNull()) //
        .isEqualTo("E:/Jenkins/jobs/Tools Development/workspace/Tools/Tools.SSOTester/Form1.cs");

    Violation actualViolationOne = actual.get(1);
    assertThat(actualViolationOne.getFile()) //
        .isEqualTo("E:/Jenkins/jobs/Tools Development/workspace/Tools/Tools.SSOTester/Form1.cs");
    assertThat(actualViolationOne.getStartLine()) //
        .isEqualTo(19);
    assertThat(actualViolationOne.getMessage()) //
        .startsWith("The field");
    assertThat(actualViolationOne.getReporter()) //
        .isEqualTo(STYLECOP.name());
    assertThat(actualViolationOne.getRule().orNull()) //
        .isEqualTo("ElementsMustBeDocumented");
    assertThat(actualViolationOne.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(actualViolationOne.getSource().orNull()) //
        .isEqualTo("E:/Jenkins/jobs/Tools Development/workspace/Tools/Tools.SSOTester/Form1.cs");
  }
}
