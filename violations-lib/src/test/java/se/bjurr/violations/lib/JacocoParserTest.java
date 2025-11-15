package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.reports.Parser.JACOCO;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class JacocoParserTest {

  @Test
  public void shouldSkipViolationUnderLineThreshold() throws Exception {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/jacoco/shouldSkipViolationUnderLineThreshold\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JACOCO) //
            .violations();

    assertThat(actual) //
        .hasSize(0);
  }

  @Test
  public void shouldSkipViolationOverCoverageThreshold() throws Exception {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/jacoco/shouldSkipViolationOverCoverageThreshold\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JACOCO) //
            .violations();

    assertThat(actual) //
        .hasSize(0);
  }

  @Test
  public void shouldParseViolation() throws Exception {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/jacoco/shouldParseViolation\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JACOCO) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .startsWith("Covered 40 out of 92 instructions (43");
    assertThat(violation0.getFile()) //
        .isEqualTo("se/bjurr/violations/lib/reports/Parser.java");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation0.getStartLine()) //
        .isEqualTo(71);
  }

  @Test
  public void shouldParseViolationsInMultipleMethods() throws Exception {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/jacoco/shouldParseViolationsInMultipleMethods\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JACOCO) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .startsWith("Covered 42 out of 92 instructions (45");
    assertThat(violation0.getFile()) //
        .isEqualTo("se/bjurr/violations/lib/reports/Parser.java");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation0.getStartLine()) //
        .isEqualTo(101);

    final Violation violation1 = new ArrayList<>(actual).get(0);
    assertThat(violation1.getMessage()) //
        .startsWith("Covered 42 out of 92 instructions (45");
    assertThat(violation1.getFile()) //
        .isEqualTo("se/bjurr/violations/lib/reports/Parser.java");
    assertThat(violation1.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation1.getStartLine()) //
        .isEqualTo(101);
  }

  @Test
  public void shouldParseViolationsInMultipleClasses() throws Exception {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/jacoco/shouldParseViolationsInMultipleClasses\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JACOCO) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .startsWith("Covered 40 out of 92 instructions (43");
    assertThat(violation0.getFile()) //
        .isEqualTo("se/bjurr/violations/lib/reports/Parser.java");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation0.getStartLine()) //
        .isEqualTo(71);

    final Violation violation1 = new ArrayList<>(actual).get(1);
    assertThat(violation1.getMessage()) //
        .startsWith("Covered 42 out of 92 instructions (45");
    assertThat(violation1.getFile()) //
        .isEqualTo("se/bjurr/violations/lib/reports/Parser2.java");
    assertThat(violation1.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation1.getStartLine()) //
        .isEqualTo(71);
  }

  @Test
  public void shouldParseViolationsInMultipleGroups() throws Exception {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/jacoco/shouldParseViolationsInMultipleGroups\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JACOCO) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .startsWith("Covered 40 out of 92 instructions (43");
    assertThat(violation0.getFile()) //
        .isEqualTo("se/bjurr/violations/lib/reports/Parser.java");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation0.getStartLine()) //
        .isEqualTo(71);

    final Violation violation1 = new ArrayList<>(actual).get(1);
    assertThat(violation1.getMessage()) //
        .startsWith("Covered 42 out of 92 instructions (45");
    assertThat(violation1.getFile()) //
        .isEqualTo("se/bjurr/violations/lib/reports/Parser2.java");
    assertThat(violation1.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation1.getStartLine()) //
        .isEqualTo(71);
  }

  @Test
  public void shouldSkipViolationWithoutLineInfo() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/jacoco/shouldSkipViolationWithoutLineInfo\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JACOCO) //
            .violations();

    assertThat(actual) //
        .hasSize(0);
  }
}
