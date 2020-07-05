package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.YAMLLINT;
import static se.bjurr.violations.lib.util.Utils.isNullOrEmpty;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

/**
 * YAMLlint.<br>
 * <code>
 * yamllint -f parsable
 * </code>
 */
public class YAMLlintParser implements ViolationsParser {
  @Override
  public List<Violation> parseReportOutput(final String string, ViolationsLogger violationsLogger)
      throws Exception {
    final List<Violation> violations = new ArrayList<>();
    final List<List<String>> partsPerLine =
        getLines(
            string, "([^:]*):(\\d+):(\\d+):\\s?\\[(error|warning)\\]([^\\(]+)(\\(([^\\)]+)\\))?");
    for (final List<String> parts : partsPerLine) {
      final String filename = parts.get(1).trim();
      final Integer line = parseInt(parts.get(2));
      final Integer column = parseInt(parts.get(3));
      final String severity = parts.get(4).trim();
      final String message = parts.get(5).trim();
      String rule = null;
      if (!isNullOrEmpty(parts.get(7))) {
        rule = parts.get(7).trim();
      }
      violations.add( //
          violationBuilder() //
              .setParser(YAMLLINT) //
              .setStartLine(line) //
              .setColumn(column) //
              .setFile(filename) //
              .setSeverity(this.toSeverity(severity)) //
              .setMessage(message) //
              .setRule(rule) //
              .build() //
          );
    }
    return violations;
  }

  /**
   *
   *
   * <pre>
   * The different message types are:
   * warning, for non critical syntax errors
   * error, for more serious syntax problem
   * </pre>
   */
  public SEVERITY toSeverity(final String severity) {
    if (severity.equalsIgnoreCase("error")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("warning")) {
      return WARN;
    }
    return INFO;
  }
}
