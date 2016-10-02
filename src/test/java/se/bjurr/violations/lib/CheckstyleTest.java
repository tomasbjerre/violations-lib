package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Reporter.CHECKSTYLE;

import java.util.List;

import org.junit.Test;

import se.bjurr.violations.lib.model.Violation;

public class CheckstyleTest {

 @Test
 public void testThatViolationsCanBeParsed() {
  String rootFolder = getRootFolder();

  List<Violation> actual = violationsReporterApi() //
    .withPattern(".*/checkstyle/.*\\.xml$") //
    .inFolder(rootFolder) //
    .findAll(CHECKSTYLE) //
    .violations();

  assertThat(actual)//
    .containsExactly(//
      violationBuilder()//
        .setReporter(CHECKSTYLE)//
        .setFile("/src/main/java/se/bjurr/violations/lib/example/MyClass.java")//
        .setSource(null)//
        .setStartLine(0)//
        .setEndLine(0)//
        .setColumn(null)//
        .setMessage("Missing package-info.java file.")//
        .setRule("com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck")//
        .setSeverity(ERROR)//
        .build(), //
      violationBuilder()//
        .setReporter(CHECKSTYLE)//
        .setFile("/src/main/java/se/bjurr/violations/lib/example/MyClass.java")//
        .setSource(null)//
        .setStartLine(9)//
        .setEndLine(9)//
        .setColumn(10)//
        .setMessage("Must have at least one statement.")//
        .setRule("com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck")//
        .setSeverity(INFO)//
        .build(), //
      violationBuilder()//
        .setReporter(CHECKSTYLE)//
        .setFile("/src/main/java/se/bjurr/violations/lib/example/OtherClass.java")//
        .setSource(null)//
        .setStartLine(10)//
        .setEndLine(10)//
        .setColumn(31)//
        .setMessage("Must have at least one statement.")//
        .setRule("com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck")//
        .setSeverity(INFO)//
        .build(), //
      violationBuilder()//
        .setReporter(CHECKSTYLE)//
        .setFile("/src/main/java/se/bjurr/violations/lib/example/OtherClass.java")//
        .setSource(null)//
        .setStartLine(26)//
        .setEndLine(26)//
        .setColumn(3)//
        .setMessage("Boolean expression complexity is 8 (max allowed is 1).")//
        .setRule("com.puppycrawl.tools.checkstyle.checks.metrics.BooleanExpressionComplexityCheck")//
        .setSeverity(WARN)//
        .build()//
  );
 }
}
