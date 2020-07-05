package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.SBTSCALAC;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class SbtScalacParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    final List<List<String>> partsPerLine =
        getLines(reportContent, "^\\[(warn|error)\\] (.*):(\\d+): (.*)$");
    for (final List<String> parts : partsPerLine) {
      final String severity = parts.get(1);
      final String fileName = parts.get(2);
      final Integer lineNumber = parseInt(parts.get(3));
      final String message = parts.get(4);
      violations.add( //
          violationBuilder() //
              .setParser(SBTSCALAC) //
              .setStartLine(lineNumber) //
              .setFile(fileName) //
              .setSeverity(this.toSeverity(severity)) //
              .setMessage(message) //
              .build() //
          );
    }
    return violations;
  }

  public SEVERITY toSeverity(final String severity) {
    if ("error".equalsIgnoreCase(severity)) {
      return ERROR;
    }
    if ("warn".equalsIgnoreCase(severity)) {
      return WARN;
    }
    return INFO;
  }
}
