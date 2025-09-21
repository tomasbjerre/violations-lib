package se.bjurr.violations.lib.model.codeclimate;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;

import com.google.gson.GsonBuilder;
import java.util.ArrayList;
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
    final String fingerprint = "fb75b24a7540f02e4fd8fa1b9e646a88434e3085a7affab97fb3f7dd0e90844b";
    final Integer begin = 123;
    final String path = "whatever/path.c";
    final Set<Violation> violationSet = new TreeSet<>();
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
                    categories,
                    new ArrayList<CodeClimateLocation>())));
  }

  @Test
  public void testThatViolationsAreGroupedWithOtherLocations() {
    final Set<Violation> violationSet = new TreeSet<>();

    violationSet.add(
        violationBuilder() //
            .setFile("whatever/path.c") //
            .setMessage("asdasd") //
            .setParser(Parser.CHECKSTYLE) //
            .setRule("Cyclomatic complexity") //
            .setSeverity(SEVERITY.ERROR) //
            .setStartLine(123) //
            .build());

    List<CodeClimate> transformed = CodeClimateTransformer.fromViolations(violationSet);

    assertThat(transformed).hasSize(1);
    assertThat(transformed.get(0).getOther_locations()).hasSize(0);

    violationSet.add(
        violationBuilder() //
            .setFile("whatever/path.c") //
            .setMessage("asdasd") //
            .setParser(Parser.CHECKSTYLE) //
            .setRule("Cyclomatic complexity") //
            .setSeverity(SEVERITY.ERROR) //
            .setStartLine(124) //
            .build());

    transformed = CodeClimateTransformer.fromViolations(violationSet);

    assertThat(transformed).hasSize(1);
    assertThat(transformed.get(0).getOther_locations()).hasSize(1);
    assertThat(transformed.get(0).getOther_locations().get(0).getLines().getBegin()).isEqualTo(123);
  }

  private String toJson(final Object o) {
    return new GsonBuilder().setPrettyPrinting().create().toJson(o);
  }
}
