package se.bjurr.violations.lib.model.codeclimate;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.reports.Parser;

public class CodeClimateTransformerTest {

  @Test
  public void testThatViolationsCanBeTransformed() {
    final String description = "asdasd";
    final String fingerprint = "cbff4965bd806607e1d6c861a530c52ef51dbc268d0e79291df3bdf4fd66847c";
    final Integer begin = 123;
    final String path = "/whatever/path.c";
    final List<CodeClimate> transformed =
        CodeClimateTransformer.fromViolations(
            Arrays.asList(
                violationBuilder() //
                    .setFile(path) //
                    .setMessage(description) //
                    .setParser(Parser.CHECKSTYLE) //
                    .setSeverity(SEVERITY.ERROR) //
                    .setStartLine(begin) //
                    .build()));
    final CodeClimateLines lines = new CodeClimateLines(begin);
    final CodeClimateLocation location = new CodeClimateLocation(path, lines, null);
    final CodeClimateSeverity severity = CodeClimateSeverity.critical;
    final String check_name = Parser.CHECKSTYLE.name();
    final List<CodeClimateCategory> categories = Arrays.asList(CodeClimateCategory.BUGRISK);
    assertThat(transformed) //
        .hasSize(1) //
        .containsOnly(
            new CodeClimate(description, fingerprint, location, severity, check_name, categories));
  }
}
