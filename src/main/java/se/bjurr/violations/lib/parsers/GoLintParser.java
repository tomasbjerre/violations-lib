package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.GOLINT;
import static se.bjurr.violations.lib.util.Utils.isNullOrEmpty;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class GoLintParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    final List<List<String>> partsPerLine =
        getLines(reportContent, "^([^:]+?):(\\d*):?(\\d*?):?([^:]*?)?:? (.*)$");
    for (final List<String> parts : partsPerLine) {
      final String fileName = parts.get(1);
      Integer lineNumber = 0;
      if (!parts.get(2).isEmpty()) {
        lineNumber = parseInt(parts.get(2));
      }
      Integer columnNumber = 0;
      if (!parts.get(3).isEmpty()) {
        columnNumber = parseInt(parts.get(3));
      }
      final String severity = parts.get(4);
      final String message = parts.get(5);
      violations.add( //
          violationBuilder() //
              .setParser(GOLINT) //
              .setStartLine(lineNumber) //
              .setColumn(columnNumber) //
              .setFile(fileName) //
              .setSeverity(this.toSeverity(severity)) //
              .setMessage(message) //
              .build() //
          );
    }
    return violations;
  }

  public SEVERITY toSeverity(final String severity) {
    if (isNullOrEmpty(severity)) {
      return INFO;
    }
    if (severity.equalsIgnoreCase("error")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("warning")) {
      return WARN;
    }
    return INFO;
  }
}
