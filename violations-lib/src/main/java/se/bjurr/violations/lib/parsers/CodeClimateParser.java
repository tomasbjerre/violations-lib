package se.bjurr.violations.lib.parsers;

import static java.util.logging.Level.FINE;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CODECLIMATE;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.model.codeclimate.CodeClimateCategory;
import se.bjurr.violations.lib.model.codeclimate.CodeClimateSeverity;
import se.bjurr.violations.lib.util.JsonMappers;

public class CodeClimateParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final List<ReadCodeClimate> codeClimate =
        JsonMappers.JSON_MAPPER.readValue(
            string,
            JsonMappers.JSON_MAPPER
                .getTypeFactory()
                .constructCollectionType(List.class, ReadCodeClimate.class));

    final Set<Violation> violations = new TreeSet<>();
    for (final ReadCodeClimate issue : codeClimate) {
      final List<CodeClimateCategory> categories = this.toCategories(issue.categories);
      if (issue.severity == null || categories.isEmpty()) {
        violationsLogger.log(FINE, "Ignoring issue: " + issue);
        continue;
      }
      Integer begin = -1;
      if (issue.location != null && issue.location.lines != null) {
        begin = issue.location.lines.begin;
      }
      if (issue.location != null
          && issue.location.positions != null
          && issue.location.positions.begin != null) {
        begin = issue.location.positions.begin.line;
      }
      if (begin == -1) {
        violationsLogger.log(FINE, "Ignoring issue: " + issue);
        continue;
      }

      violations.add(
          violationBuilder() //
              .setFile(issue.location.path) //
              .setCategory(categories.get(0).getName()) //
              .setMessage(issue.description) //
              .setParser(CODECLIMATE) //
              .setReporter(issue.engine_name) //
              .setRule(issue.check_name) //
              .setSeverity(this.toSeverity(issue.severity)) //
              .setStartLine(begin) //
              .build());
    }
    return violations;
  }

  private List<CodeClimateCategory> toCategories(final List<String> categoryNames) {
    final List<CodeClimateCategory> categories = new ArrayList<>();
    if (categoryNames == null) {
      return categories;
    }
    for (final CodeClimateCategory candidate : CodeClimateCategory.values()) {
      if (categoryNames.contains(candidate.getName())) {
        categories.add(candidate);
      }
    }
    return categories;
  }

  private SEVERITY toSeverity(final CodeClimateSeverity severity) {
    if (severity == CodeClimateSeverity.blocker
        || severity == CodeClimateSeverity.critical
        || severity == CodeClimateSeverity.major) {
      return SEVERITY.ERROR;
    }
    if (severity == CodeClimateSeverity.minor) {
      return SEVERITY.WARN;
    }
    return SEVERITY.INFO;
  }

  @SuppressFBWarnings({
    "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD",
    "NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"
  })
  private static class ReadCodeClimate {
    public String description;
    public String fingerprint;
    public ReadLocation location;
    public CodeClimateSeverity severity;
    public String check_name;
    public String engine_name;
    public List<String> categories;

    @Override
    public String toString() {
      return "ReadCodeClimate [description="
          + this.description
          + ", fingerprint="
          + this.fingerprint
          + ", location="
          + this.location
          + ", severity="
          + this.severity
          + ", check_name="
          + this.check_name
          + ", engine_name="
          + this.engine_name
          + ", categories="
          + this.categories
          + "]";
    }
  }

  @SuppressFBWarnings({
    "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD",
    "NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"
  })
  private static class ReadLocation {
    public String path;
    public ReadLines lines;
    public ReadPositions positions;
  }

  @SuppressFBWarnings({
    "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD",
    "NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"
  })
  private static class ReadLines {
    public Integer begin;
  }

  @SuppressFBWarnings({
    "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD",
    "NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"
  })
  private static class ReadPositions {
    public ReadPosition begin;
  }

  @SuppressFBWarnings({
    "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD",
    "NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"
  })
  private static class ReadPosition {
    public Integer line;
  }
}
