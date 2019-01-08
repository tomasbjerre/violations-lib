package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.CODENARC;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class CodeNarcTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/codenarc/CodeNarc.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CODENARC) //
            .violations();

    assertThat(actual) //
        .hasSize(32);

    final Violation violation0 = actual.get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("In most cases, exceptions should not be caught and ignored (swallowed).");
    assertThat(violation0.getFile()) //
        .isEqualTo("/foo/bar/Test.groovy");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("EmptyCatchBlock");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(192);
    assertThat(violation0.getEndLine()) //
        .isEqualTo(192);

    assertThat(actual.get(2).getMessage()) //
        .isEqualTo(
            "Catching Exception is often too broad or general. It should usually be restricted to framework or infrastructure code, rather than application code.");
  }

  @Test
  public void testThatViolationsCanBeParsed2() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/codenarc/SampleCodeNarc.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CODENARC) //
            .violations();

    assertThat(actual) //
        .hasSize(79);

    final Violation violation0 = actual.get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo(
            "Violations are triggered when an excessive set of consecutive statements all reference the same variable. This can be made more readable by using a with or identity block.");
    assertThat(violation0.getFile()) //
        .isEqualTo("src/test/groovy/org/codenarc/rule/AbstractAstVisitorRuleTest.groovy");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(violation0.getRule()) //
        .isEqualTo("UnnecessaryObjectReferences");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(184);
    assertThat(violation0.getEndLine()) //
        .isEqualTo(184);
  }
}
