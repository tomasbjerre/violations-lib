package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.MSCPP;
import static se.bjurr.violations.lib.util.Utils.isNullOrEmpty;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class MSCPPParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(final String reportContent) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    final List<List<String>> partsPerLine =
        getLines(reportContent, "(.*)\\((\\d+)\\):\\s*([a-zA-Z]+)([^:]*):(.*)");
    for (final List<String> parts : partsPerLine) {
      final String fileName = parts.get(1).trim();
      final Integer lineNumber = parseInt(parts.get(2));
      final Integer columnNumber = 0;
      final String severity = parts.get(3).toLowerCase().trim();
      final String rule = parts.get(4).trim();
      final String message = parts.get(5).trim();
      violations.add( //
          violationBuilder() //
              .setParser(MSCPP) //
              .setStartLine(lineNumber) //
              .setColumn(columnNumber) //
              .setFile(fileName) //
              .setSeverity(toSeverity(severity)) //
              .setRule(rule) //
              .setMessage(message) //
              .build() //
          );
    }
    return violations;
  }

  public SEVERITY toSeverity(final String severity) {
    if (isNullOrEmpty(severity) || severity.contains("info")) {
      return INFO;
    }
    if (severity.contains("error")) {
      return ERROR;
    }
    if (severity.contains("warning")) {
      return WARN;
    }
    return INFO;
  }
}
