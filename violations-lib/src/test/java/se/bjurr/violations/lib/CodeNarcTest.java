package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.CODENARC;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class CodeNarcTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/codenarc/CodeNarc.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CODENARC) //
            .violations();

    assertThat(actual) //
        .hasSize(32);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("In most cases, exceptions should not be caught and ignored (swallowed).");
    assertThat(violation0.getFile()) //
        .isEqualTo("/Test.groovy");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("EmptyCatchBlock");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(192);
    assertThat(violation0.getEndLine()) //
        .isEqualTo(192);

    assertThat(new ArrayList<>(actual).get(2).getMessage()) //
        .isEqualTo("Checks for throwing an instance of java.lang.Exception.");
  }

  @Test
  public void testThatViolationsCanBeParsed2() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/codenarc/SampleCodeNarc.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CODENARC) //
            .violations();

    assertThat(actual) //
        .hasSize(76);

    final Violation violation0 = new ArrayList<>(actual).get(0);
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
        .isEqualTo(188);
    assertThat(violation0.getEndLine()) //
        .isEqualTo(188);
  }

  @Test
  public void testThatViolationsCanBeParsedEmptySourceFolder() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/codenarc/CodeNarcXmlReport\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CODENARC) //
            .violations();

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getFile()) //
        .isEqualTo("grails-app/controllers/LoginController.groovy");
  }
}
