package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CLANG;
import static se.bjurr.violations.lib.util.Utils.isNullOrEmpty;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class CLangParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(final String reportContent) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    final List<List<String>> partsPerLine =
        getLines(reportContent, "^([^:]+?):(\\d*):((\\d*?):)?([^:]*?)?: (.*)$");
    for (final List<String> parts : partsPerLine) {
      final String fileName = parts.get(1);
      Integer lineNumber = 0;
      if (!parts.get(2).isEmpty()) {
        lineNumber = parseInt(parts.get(2));
      }
      Integer columnNumber = 0;
      if (parts.get(3) != null
          && !parts.get(3).isEmpty()
          && parts.get(4) != null
          && !parts.get(4).isEmpty()) {
        columnNumber = parseInt(parts.get(4));
      }
      final String severity = parts.get(5);
      final String message = parts.get(6);
      violations.add( //
          violationBuilder() //
              .setParser(CLANG) //
              .setStartLine(lineNumber) //
              .setColumn(columnNumber) //
              .setFile(fileName) //
              .setSeverity(toSeverity(severity)) //
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
    if (severity.contains("error") || severity.contains("C")) {
      return ERROR;
    }
    if (severity.contains("warning") || severity.contains("W")) {
      return WARN;
    }
    return INFO;
  }
}
