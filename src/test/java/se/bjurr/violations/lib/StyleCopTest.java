package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Reporter.STYLECOP;

import java.util.List;

import org.junit.Test;

import se.bjurr.violations.lib.model.Violation;

public class StyleCopTest {

 @Test
 public void testThatViolationsCanBeParsed() {
  String rootFolder = getRootFolder();

  List<Violation> actual = violationsReporterApi() //
    .withPattern(".*/stylecop/.*\\.xml$") //
    .inFolder(rootFolder) //
    .findAll(STYLECOP) //
    .violations();

  assertThat(actual)//
    .hasSize(2);

  Violation actualViolationZero = actual.get(0);
  assertThat(actualViolationZero.getFile())//
    .isEqualTo("Form1/Designer/cs");
  assertThat(actualViolationZero.getStartLine())//
    .isEqualTo(18);
  assertThat(actualViolationZero.getMessage())//
    .startsWith("The call to");
  assertThat(actualViolationZero.getReporter())//
    .isEqualTo(STYLECOP);
  assertThat(actualViolationZero.getRule().orNull())//
    .isEqualTo("PrefixLocalCallsWithThis");
  assertThat(actualViolationZero.getSeverity())//
    .isEqualTo(INFO);
  assertThat(actualViolationZero.getSource().orNull())//
    .isEqualTo("Form1.Designer.cs");

  Violation actualViolationOne = actual.get(1);
  assertThat(actualViolationOne.getFile())//
    .isEqualTo("Form1/Designer/cs");
  assertThat(actualViolationOne.getStartLine())//
    .isEqualTo(16);
  assertThat(actualViolationOne.getMessage())//
    .startsWith("The call to");
  assertThat(actualViolationOne.getReporter())//
    .isEqualTo(STYLECOP);
  assertThat(actualViolationOne.getRule().orNull())//
    .isEqualTo("PrefixLocalCallsWithThis");
  assertThat(actualViolationOne.getSeverity())//
    .isEqualTo(INFO);
  assertThat(actualViolationOne.getSource().orNull())//
    .isEqualTo("Form1.Designer.cs");

 }
}
