package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.filterRule;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.parsers.FindbugsParser.FINDBUGS_RULE_ANALYSIS_ERROR;
import static se.bjurr.violations.lib.parsers.FindbugsParser.FINDBUGS_RULE_MISSING_CLASS;
import static se.bjurr.violations.lib.parsers.FindbugsParser.FINDBUGS_SPECIFIC_RANK;
import static se.bjurr.violations.lib.reports.Parser.FINDBUGS;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class FindbugsTest {

  @Test
  public void testMavenGeneratedFindbugs() {
    final String rootFolder = getRootFolder();
    final Set<Violation> maven =
        violationsApi() //
            .withPattern(".*/findbugs/fromMaven.xml$") //
            .inFolder(rootFolder) //
            .findAll(FINDBUGS) //
            .violations();

    assertThat(maven) //
        .hasSize(1);
  }

  @Test
  public void testMavenGeneratedSpotbugs() {
    final String rootFolder = getRootFolder();
    final Set<Violation> maven =
        violationsApi() //
            .findAll(FINDBUGS) //
            .inFolder(rootFolder) //
            .withPattern(".*/findbugs/spotbugs-single-bug.xml$") //
            .withReporter("Spotbugs")
            .violations();

    assertThat(maven) //
        .hasSize(1);
  }

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();
    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/findbugs/main\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(FINDBUGS) //
            .violations();

    Iterable<Violation> equalsUseHashCode = filterRule(actual, "NM_FIELD_NAMING_CONVENTION");
    assertThat(equalsUseHashCode) //
        .hasSize(1);

    equalsUseHashCode = filterRule(actual, "HE_EQUALS_USE_HASHCODE");
    assertThat(equalsUseHashCode) //
        .hasSize(1);

    assertThat(actual) //
        .hasSize(5);
    final Violation violation0 = new ArrayList<>(actual).get(1);
    assertThat(violation0.getFile()) //
        .isEqualTo("se/bjurr/violations/lib/example/MyClass.java");
    assertThat(violation0.getMessage()) //
        .startsWith("equals method always returns true") //
        .doesNotContain("CDATA");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(17);
    assertThat(violation0.getEndLine()) //
        .isEqualTo(17);
    assertThat(violation0.getRule()) //
        .isEqualTo("EQ_ALWAYS_TRUE");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation0.getSource()) //
        .isEqualTo("se.bjurr.violations.lib.example.MyClass");
    assertThat(violation0.getSpecifics().get(FINDBUGS_SPECIFIC_RANK)) //
        .isEqualTo("7");
  }

  @Test
  public void testThatViolationsCanBeParsedFromSpotbugs() {
    final String rootFolder = getRootFolder();
    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/findbugs/spotbugs\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(FINDBUGS) //
            .violations();

    assertThat(actual) //
        .hasSize(3);
    final Violation violation0 = new ArrayList<>(actual).get(1);
    assertThat(violation0.getFile()) //
        .isEqualTo("se/bjurr/violations/lib/parsers/FindbugsParser.java");
    assertThat(violation0.getMessage()) //
        .startsWith("Method invokes toString") //
        .doesNotContain("CDATA");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(125);
    assertThat(violation0.getEndLine()) //
        .isEqualTo(125);
    assertThat(violation0.getRule()) //
        .isEqualTo("DM_STRING_TOSTRING");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(violation0.getSource()) //
        .isEqualTo("se.bjurr.violations.lib.parsers.FindbugsParser");
    assertThat(violation0.getSpecifics().get(FINDBUGS_SPECIFIC_RANK)) //
        .isEqualTo("20");
  }

  @Test
  public void testThatSrcDirIsCombinedWithSourcepathWhenExactlyOneSrcDir() {
    final String rootFolder = getRootFolder();
    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/findbugs/spotbugs-one-srcdir\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(FINDBUGS) //
            .violations();

    assertThat(actual) //
        .hasSize(1);
    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getFile()) //
        .isEqualTo("/builds/my-project/module-a/src/main/java/com/example/Foo.java");
  }

  @Test
  public void testThatViolationsCanBeParsedFromSpotbugs2() {
    final String rootFolder = getRootFolder();
    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/findbugs/spotbugs-main\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(FINDBUGS) //
            .violations();

    assertThat(actual) //
        .hasSize(3);
    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getFile()) //
        .isEqualTo("se/bjurr/violations/lib/reports/ReportsFinder.java");
  }

  @Test
  public void testThatMissingClassesAndErrorsAreParsed() {
    final String rootFolder = getRootFolder();
    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/findbugs/spotbugs-missing-class-and-error\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(FINDBUGS) //
            .violations();

    assertThat(actual) //
        .hasSize(3);

    final Iterable<Violation> errors = filterRule(actual, FINDBUGS_RULE_ANALYSIS_ERROR);
    assertThat(errors) //
        .hasSize(1);
    final Violation error = errors.iterator().next();
    assertThat(error.getMessage()) //
        .startsWith("Error scanning class com.example.Foo") //
        .contains("java.lang.RuntimeException: bad class file") //
        .contains("edu.umd.cs.findbugs.Foo.bar(Foo.java:10)") //
        .contains("edu.umd.cs.findbugs.Foo.baz(Foo.java:20)");
    assertThat(error.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(error.getFile()) //
        .isEqualTo(Violation.NO_FILE);

    final Iterable<Violation> missingClasses = filterRule(actual, FINDBUGS_RULE_MISSING_CLASS);
    assertThat(missingClasses) //
        .hasSize(2);
    final Violation missingClass = missingClasses.iterator().next();
    assertThat(missingClass.getMessage()) //
        .contains("com.example.");
    assertThat(missingClass.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(missingClass.getFile()) //
        .isEqualTo(Violation.NO_FILE);
  }
}
