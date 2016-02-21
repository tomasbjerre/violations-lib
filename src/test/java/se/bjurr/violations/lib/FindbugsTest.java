package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.parsers.FindbugsParser.FINDBUGS_SPECIFIC_RANK;
import static se.bjurr.violations.lib.reports.Reporter.FINDBUGS;

import java.util.List;

import org.junit.Test;

import se.bjurr.violations.lib.model.Violation;

public class FindbugsTest {

 @Test
 public void testThatViolationsCanBeParsed() {
  String rootFolder = getRootFolder();

  List<Violation> actual = violationsApi() //
    .withPattern(".*/findbugs/.*\\.xml$") //
    .inFolder(rootFolder) //
    .findAll(FINDBUGS) //
    .violations();

  assertThat(actual)//
    .hasSize(13);

  assertThat(actual.get(0).getFile())//
    .isEqualTo("se/bjurr/violations/lib/example/MyClass.java");
  assertThat(actual.get(0).getMessage())//
    .startsWith("equals method always returns true")//
    .doesNotContain("CDATA");
  assertThat(actual.get(0).getStartLine())//
    .isEqualTo(3);
  assertThat(actual.get(0).getEndLine())//
    .isEqualTo(17);
  assertThat(actual.get(0).getRule().get())//
    .isEqualTo("EQ_ALWAYS_TRUE");
  assertThat(actual.get(0).getSeverity())//
    .isEqualTo(ERROR);
  assertThat(actual.get(0).getSource().get())//
    .isEqualTo("se.bjurr.violations.lib.example.MyClass");
  assertThat(actual.get(0).getSpecifics().get(FINDBUGS_SPECIFIC_RANK))//
    .isEqualTo("7");
 }
}
