package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Reporter.PITEST;

import java.util.List;

import org.junit.Test;

import se.bjurr.violations.lib.model.Violation;

public class PiTestTest {

 @Test
 public void testThatViolationsCanBeParsed() {
  String rootFolder = getRootFolder();

  List<Violation> actual = violationsReporterApi() //
    .withPattern(".*/pitest/.*\\.xml$") //
    .inFolder(rootFolder) //
    .findAll(PITEST) //
    .violations();

  assertThat(actual)//
    .hasSize(25);

  assertThat(actual.get(0).getFile())//
    .isEqualTo("se/bjurr/violations/lib/example/CopyOfMyClass.java");
  assertThat(actual.get(0).getMessage())//
    .isEqualTo("NO_COVERAGE, org.pitest.mutationtest.engine.gregor.mutators.ReturnValsMutator, (Ljava/lang/Object;)Z");
  assertThat(actual.get(0).getStartLine())//
    .isEqualTo(17);
  assertThat(actual.get(0).getEndLine())//
    .isEqualTo(17);
  assertThat(actual.get(0).getSeverity())//
    .isEqualTo(WARN);
 }
}
