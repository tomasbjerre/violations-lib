package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Reporter.FXCOP;

import java.util.List;

import org.junit.Test;

import se.bjurr.violations.lib.model.Violation;

public class FxCopTest {

 @Test
 public void testThatViolationsCanBeParsed() {
  String rootFolder = getRootFolder();

  List<Violation> actual = violationsReporterApi() //
    .withPattern(".*/fxcop/.*\\.xml$") //
    .inFolder(rootFolder) //
    .findAll(FXCOP) //
    .violations();

  assertThat(actual)//
    .hasSize(25);

  Violation actualViolationZero = actual.get(0);
  assertThat(actualViolationZero.getFile())//
    .isEqualTo("C:/git/test-project/Test Solution 1/GenericsSample/Form1.Designer.cs");
  assertThat(actualViolationZero.getStartLine())//
    .isEqualTo(212);
  assertThat(actualViolationZero.getMessage())//
    .startsWith("Method 'Form");
  assertThat(actualViolationZero.getReporter())//
    .isEqualTo(FXCOP);
  assertThat(actualViolationZero.getRule().orNull())//
    .isEqualTo("Do not pass literals as localized parameters");
  assertThat(actualViolationZero.getSeverity())//
    .isEqualTo(WARN);
  assertThat(actualViolationZero.getSource().orNull())//
    .isEqualTo("Form1");
  assertThat(actualViolationZero.getSpecifics().get("TARGET_NAME"))//
    .isEqualTo("C:/git/test-project/Test Solution 1/GenericsSample/bin/Debug/GenericsSample.exe");

  Violation actualViolationOne = actual.get(1);
  assertThat(actualViolationOne.getFile())//
    .isEqualTo("C:/git/test-project/Test Solution 1/GenericsSample/Form1.Designer.cs");
  assertThat(actualViolationOne.getStartLine())//
    .isEqualTo(203);
  assertThat(actualViolationOne.getMessage())//
    .startsWith("Method 'Form");
  assertThat(actualViolationOne.getReporter())//
    .isEqualTo(FXCOP);
  assertThat(actualViolationOne.getRule().orNull())//
    .isEqualTo("Do not pass literals as localized parameters");
  assertThat(actualViolationOne.getSeverity())//
    .isEqualTo(WARN);
  assertThat(actualViolationOne.getSource().orNull())//
    .isEqualTo("Form1");
  assertThat(actualViolationOne.getSpecifics().get("TARGET_NAME"))//
    .isEqualTo("C:/git/test-project/Test Solution 1/GenericsSample/bin/Debug/GenericsSample.exe");

 }
}
