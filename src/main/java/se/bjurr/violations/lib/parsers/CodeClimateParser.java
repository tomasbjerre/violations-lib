package se.bjurr.violations.lib.parsers;

import static java.util.logging.Level.FINE;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CODECLIMATE;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.model.codeclimate.CodeClimate;
import se.bjurr.violations.lib.model.codeclimate.CodeClimateSeverity;

public class CodeClimateParser implements ViolationsParser {
  private static final Type listType = new TypeToken<List<CodeClimate>>() {}.getType();

  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final List<CodeClimate> codeClimate = new Gson().fromJson(string, listType);

    final Set<Violation> violations = new TreeSet<>();
    for (final CodeClimate issue : codeClimate) {
      if (issue.getSeverity() == null || issue.getCategories().size() == 0) {
        violationsLogger.log(FINE, "Ignoring issue: " + issue);
        continue;
      }
      Integer begin = -1;
      if (issue.getLocation() != null && issue.getLocation().getLines() != null) {
        begin = issue.getLocation().getLines().getBegin();
      }
      if (issue.getLocation() != null
          && issue.getLocation().getPositions() != null
          && issue.getLocation().getPositions().getBegin() != null) {
        begin = issue.getLocation().getPositions().getBegin().getLine();
      }
      if (begin == -1) {
        violationsLogger.log(FINE, "Ignoring issue: " + issue);
        continue;
      }

      violations.add(
          violationBuilder() //
              .setFile(issue.getLocation().getPath()) //
              .setCategory(issue.getCategories().get(0).getName()) //
              .setMessage(issue.getDescription()) //
              .setParser(CODECLIMATE) //
              .setReporter(issue.getEngine_name()) //
              .setRule(issue.getCheck_name()) //
              .setSeverity(this.toSeverity(issue.getSeverity())) //
              .setStartLine(begin) //
              .build());
    }
    return violations;
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
}
