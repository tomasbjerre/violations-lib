package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.FXCOP;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class FxCopTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/fxcop/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(FXCOP) //
            .violations();

    assertThat(actual) //
        .hasSize(25);

    final Violation actualViolationZero = actual.get(0);
    assertThat(actualViolationZero.getFile()) //
        .isEqualTo("C:/git/test-project/Test Solution 1/GenericsSample/Form1.Designer.cs");
    assertThat(actualViolationZero.getStartLine()) //
        .isEqualTo(288);
    assertThat(actualViolationZero.getMessage()) //
        .startsWith("Method 'Form");
    assertThat(actualViolationZero.getReporter()) //
        .isEqualTo(FXCOP.name());
    assertThat(actualViolationZero.getRule()) //
        .isEqualTo("Do not pass literals as localized parameters");
    assertThat(actualViolationZero.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(actualViolationZero.getSource()) //
        .isEqualTo("Form1");
    assertThat(actualViolationZero.getSpecifics().get("TARGET_NAME")) //
        .isEqualTo(
            "C:/git/test-project/Test Solution 1/GenericsSample/bin/Debug/GenericsSample.exe");

    final Violation actualViolationOne = actual.get(1);
    assertThat(actualViolationOne.getFile()) //
        .isEqualTo("C:/git/test-project/Test Solution 1/GenericsSample/Form1.Designer.cs");
    assertThat(actualViolationOne.getStartLine()) //
        .isEqualTo(275);
    assertThat(actualViolationOne.getMessage()) //
        .startsWith("Correct the spell");
    assertThat(actualViolationOne.getReporter()) //
        .isEqualTo(FXCOP.name());
    assertThat(actualViolationOne.getRule()) //
        .isEqualTo("Literals should be spelled correctly");
    assertThat(actualViolationOne.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(actualViolationOne.getSource()) //
        .isEqualTo("Form1");
    assertThat(actualViolationOne.getSpecifics().get("TARGET_NAME")) //
        .isEqualTo(
            "C:/git/test-project/Test Solution 1/GenericsSample/bin/Debug/GenericsSample.exe");
  }
}
