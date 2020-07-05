package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.PCLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getParts;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class PCLintParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    final List<String> lines = getLines(string);
    final Pattern misraPattern = Pattern.compile("\\[MISRA.*\\]");
    for (final String line : lines) {
      final Matcher misraMatcher = misraPattern.matcher(line);
      if (misraMatcher.find()) {
        this.parseMisraViolation(line, violations);
      } else {
        this.parseGeneralViolation(line, violations);
      }
    }
    return violations;
  }

  private void parseMisraViolation(final String line, final Set<Violation> violations) {
    final List<String> parts =
        getParts(
            line,
            "^([^\\(]+)\\(",
            "^([\\d]+)\\): ",
            "^(?:Error|Warning|Info|Note) [\\d]+: ([^\\[]*)",
            "^\\[(.*),",
            "(mandatory|required|advisory)\\]",
            "^(.*)$");
    if (parts.isEmpty()) {
      return;
    }
    final String filename = parts.get(0);
    final Integer lineNumber = parseInt(parts.get(1));

    final String severityString = parts.get(4);
    final SEVERITY severity = this.toMisraSeverity(severityString);
    final String rule = parts.get(3) + ", " + severityString;
    final String message = parts.get(2) + " " + parts.get(5);
    violations.add( //
        violationBuilder() //
            .setParser(PCLINT) //
            .setStartLine(lineNumber) //
            .setFile(filename) //
            .setRule(rule) //
            .setSeverity(severity) //
            .setMessage(message) //
            .build() //
        );
  }

  private void parseGeneralViolation(final String line, final Set<Violation> violations) {
    final List<String> parts =
        getParts(
            line,
            "^([^\\(]+)\\(",
            "^([\\d]+)\\): ",
            "^(Error|Warning|Info|Note) ",
            "^([\\d]+): ",
            "^(.*)$");
    if (parts.isEmpty()) {
      return;
    }
    final String filename = parts.get(0);
    final Integer lineNumber = parseInt(parts.get(1));
    final SEVERITY severity = this.toSeverity(parts.get(2));
    final String rule = parts.get(3);
    final String message = parts.get(4);
    violations.add( //
        violationBuilder() //
            .setParser(PCLINT) //
            .setStartLine(lineNumber) //
            .setFile(filename) //
            .setRule(rule) //
            .setSeverity(severity) //
            .setMessage(message) //
            .build() //
        );
  }

  private SEVERITY toSeverity(final String severity) {
    if (severity.equals("Error")) {
      return ERROR;
    }
    if (severity.equals("Warning")) {
      return WARN;
    }
    return INFO;
  }

  private SEVERITY toMisraSeverity(final String severity) {
    if (severity.equals("mandatory")) {
      return ERROR;
    }
    if (severity.equals("required")) {
      return WARN;
    }
    return INFO;
  }
}
