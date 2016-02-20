package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Reporter.CSSLINT;

import java.util.List;

import org.junit.Test;

import se.bjurr.violations.lib.model.Violation;

public class CSSLintTest {

 @Test
 public void testThatViolationsCanBeParsed() {
  String rootFolder = getRootFolder();

  List<Violation> actual = violationsApi() //
    .withPattern(".*/csslint/.*\\.xml$") //
    .inFolder(rootFolder) //
    .findAll(CSSLINT) //
    .violations();

  assertThat(actual)//
    .containsExactly(//
      violationBuilder()//
        .setFile("web/css-file.css")//
        .setSource(null)//
        .setStartLine(3)//
        .setEndLine(3)//
        .setMessage("Duplicate property 'font-size' found.: font-size: 14px;")//
        .setRule(null)//
        .setSeverity(WARN)//
        .build(), //
      violationBuilder()//
        .setFile("web/css-file2.css")//
        .setSource(null)//
        .setStartLine(2)//
        .setEndLine(2)//
        .setMessage("Unknown property 'thisisnotarule'.: thisisnotarule: 1;")//
        .setRule(null)//
        .setSeverity(WARN)//
        .build(), //
      violationBuilder()//
        .setFile("web/css-file2.css")//
        .setSource(null)//
        .setStartLine(3)//
        .setEndLine(3)//
        .setMessage("Unknown property 'thisisalsonotarule'.: thisisalsonotarule: 1;")//
        .setRule(null)//
        .setSeverity(WARN)//
        .build() //
    );
 }
}
