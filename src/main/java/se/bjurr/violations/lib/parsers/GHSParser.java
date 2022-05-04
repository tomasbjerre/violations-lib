package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.GHS;
import static se.bjurr.violations.lib.util.Utils.isNullOrEmpty;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class GHSParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    final List<List<String>> partsPerLine =
        getLines(reportContent, "^([^:]+?),\\D*(\\d+)?:\\s*(\\D*\\d+\\D*):\\s*(.*)$");
    for (final List<String> parts : partsPerLine) {
      final String fileName = parts.get(1);
      Integer lineNumber = 0;
      if (!parts.get(2).isEmpty()) {
        lineNumber = parseInt(parts.get(2));
      }
      final String severity = parts.get(3);
      final String message = parts.get(4);
      violations.add( //
          violationBuilder() //
              .setParser(GHS) //
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
    if (isNullOrEmpty(severity)) {
      return INFO;
    }
    final String lowerCase = severity.toLowerCase(Locale.ENGLISH);
    if (lowerCase.contains("low")) {
      return INFO;
    }
    if (lowerCase.contains("error") || lowerCase.contains("c") || lowerCase.contains("high")) {
      return ERROR;
    }
    if (lowerCase.contains("warning") || lowerCase.contains("w") || lowerCase.contains("medium")) {
      return WARN;
    }
    return INFO;
  }
}
