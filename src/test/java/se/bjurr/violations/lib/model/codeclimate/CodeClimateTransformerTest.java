package se.bjurr.violations.lib.model.codeclimate;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;

import com.google.gson.GsonBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;

public class CodeClimateTransformerTest {

  @Test
  public void testThatViolationsCanBeTransformed() {
    final String description = "asdasd";
    final String fingerprint = "287f089bbb587fbb815c35558f2053564c792d5add0f19cfd38fc6ffea3454fc";
    final Integer begin = 123;
    final String path = "/whatever/path.c";
    final Set<Violation> violationSet = new TreeSet<Violation>();
    final Violation violation1 =
        violationBuilder() //
            .setFile(path) //
            .setMessage(description) //
            .setParser(Parser.CHECKSTYLE) //
            .setRule("Cyclomatic complexity") //
            .setSeverity(SEVERITY.ERROR) //
            .setStartLine(begin) //
            .build();
    violationSet.add(violation1);
    final Violation violation2 =
        violationBuilder() //
            .setFile(path) //
            .setMessage(description) //
            .setParser(Parser.ANDROIDLINT) //
            .setRule(null) //
            .setSeverity(SEVERITY.INFO) //
            .setStartLine(begin) //
            .build();
    violationSet.add(violation2);
    final List<CodeClimate> transformed = CodeClimateTransformer.fromViolations(violationSet);
    final CodeClimateLines lines = new CodeClimateLines(begin);
    final CodeClimateLocation location = new CodeClimateLocation(path, lines, null);
    final CodeClimateSeverity severity = CodeClimateSeverity.critical;
    final String check_name = "Cyclomatic complexity";
    final String engine_name = Parser.CHECKSTYLE.name();
    final List<CodeClimateCategory> categories = Arrays.asList(CodeClimateCategory.BUGRISK);
    assertThat(transformed).hasSize(2);
    assertThat(this.toJson(transformed.get(1))) //
        .isEqualTo(
            this.toJson(
                new CodeClimate(
                    description,
                    fingerprint,
                    location,
                    severity,
                    check_name,
                    engine_name,
                    categories)));
  }

  private String toJson(final Object o) {
    return new GsonBuilder().setPrettyPrinting().create().toJson(o);
  }
}
