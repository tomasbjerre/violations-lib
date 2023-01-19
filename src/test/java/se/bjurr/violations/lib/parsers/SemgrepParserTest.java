package se.bjurr.violations.lib.parsers;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.SEMGREP;

import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class SemgrepParserTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    Set<Violation> violations =
        violationsApi()
            .withPattern(".*/semgrep/semgrep-report\\.json$")
            .inFolder(getRootFolder())
            .findAll(SEMGREP)
            .violations();

    assertThat(violations).hasSize(5);
  }

  @Test
  public void testThatViolationFieldsArePopulated() {
    Set<Violation> violations =
        violationsApi()
            .withPattern(".*/semgrep/semgrep-report\\.json$")
            .inFolder(getRootFolder())
            .findAll(SEMGREP)
            .violations();

    assertThat(violations)
        .contains(
            violationBuilder()
                .setParser(SEMGREP)
                .setStartLine(33)
                .setColumn(24)
                .setEndLine(33)
                .setEndColumn(86)
                .setFile("src/main/java/se/bjurr/violations/lib/parsers/JUnitParser.java")
                .setSeverity(SEVERITY.ERROR)
                .setRule("java.lang.security.audit.formatted-sql-string.formatted-sql-string")
                .setMessage(
                    "Detected a formatted string in a SQL statement. This could lead to SQL"
                        + " injection if variables in the SQL statement are not properly sanitized."
                        + " Use a prepared statements (java.sql.PreparedStatement) instead. You can"
                        + " obtain a PreparedStatement using 'connection.prepareStatement'.")
                .build());
  }

  @Test
  public void testThatViolationSeverityIsParsed() {
    Set<Violation> violations =
        violationsApi()
            .withPattern(".*/semgrep/semgrep-report\\.json$")
            .inFolder(getRootFolder())
            .findAll(SEMGREP)
            .violations();

    assertThat(violations).anyMatch(violation -> violation.getSeverity() == SEVERITY.INFO);
    assertThat(violations).anyMatch(violation -> violation.getSeverity() == SEVERITY.WARN);
    assertThat(violations).anyMatch(violation -> violation.getSeverity() == SEVERITY.ERROR);
    assertThat(violations).noneMatch(violation -> violation.getSeverity() == null);
  }

  @Test
  public void testThatIgnoredViolationIsSkipped() {
    Set<Violation> violations =
        violationsApi()
            .withPattern(".*/semgrep/semgrep-report\\.json$")
            .inFolder(getRootFolder())
            .findAll(SEMGREP)
            .violations();

    assertThat(violations)
        .doesNotContain(
            violationBuilder()
                .setParser(SEMGREP)
                .setStartLine(43)
                .setColumn(24)
                .setEndLine(43)
                .setEndColumn(61)
                .setFile("src/main/java/se/bjurr/violations/lib/parsers/SemgrepParser.java")
                .setSeverity(SEVERITY.ERROR)
                .setRule("java.lang.security.audit.formatted-sql-string.formatted-sql-string")
                .setMessage(
                    "Detected a formatted string in a SQL statement. This could lead to SQL"
                        + " injection if variables in the SQL statement are not properly sanitized."
                        + " Use a prepared statements (java.sql.PreparedStatement) instead. You can"
                        + " obtain a PreparedStatement using 'connection.prepareStatement'.")
                .build());
  }
}
