package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
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
    .hasSize(2);

  Violation actualViolationZero = actual.get(0);
  assertThat(actualViolationZero.getFile())//
    .isEqualTo("c:/Hudson/data/jobs/job1/workspace/test/Space/TestBase.cs");
  assertThat(actualViolationZero.getStartLine())//
    .isEqualTo(299);
  assertThat(actualViolationZero.getMessage())//
    .startsWith("Because the behavior o");
  assertThat(actualViolationZero.getReporter())//
    .isEqualTo(FXCOP);
  assertThat(actualViolationZero.getRule().orNull())//
    .isEqualTo("SpecifyIFormatProvider");
  assertThat(actualViolationZero.getSeverity())//
    .isEqualTo(ERROR);
  assertThat(actualViolationZero.getSource().orNull())//
    .isEqualTo("TestBase");
  assertThat(actualViolationZero.getSpecifics().get("TARGET_NAME"))//
    .isEqualTo("C:/Hudson/data/jobs/job1/workspace/test/bin/test.dll");

  Violation actualViolationOne = actual.get(1);
  assertThat(actualViolationOne.getFile())//
    .isEqualTo("c:/Hudson/data/jobs/job1/workspace/web/UserControls/MyControl.ascx.cs");
  assertThat(actualViolationOne.getStartLine())//
    .isEqualTo(37);
  assertThat(actualViolationOne.getMessage())//
    .startsWith("In member");
  assertThat(actualViolationOne.getReporter())//
    .isEqualTo(FXCOP);
  assertThat(actualViolationOne.getRule().orNull())//
    .isEqualTo("CompoundWordsShouldBeCasedCorrectly");
  assertThat(actualViolationOne.getSeverity())//
    .isEqualTo(ERROR);
  assertThat(actualViolationOne.getSource().orNull())//
    .isEqualTo("MyControl");
  assertThat(actualViolationOne.getSpecifics().get("TARGET_NAME"))//
    .isEqualTo("C:/Hudson/data/jobs/job1/workspace/web/bin/web.dll");

 }
}
