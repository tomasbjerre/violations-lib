package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.*;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getLines;
import static se.bjurr.violations.lib.reports.Parser.SBTSCALAC;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class SbtScalacParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String reportContent) throws Exception {
    List<Violation> violations = new ArrayList<>();
    List<List<String>> partsPerLine =
        getLines(reportContent, "^\\[(warn|error)\\] (.*):(\\d+): (.*)$");
    for (List<String> parts : partsPerLine) {
      String severity = parts.get(1);
      String fileName = parts.get(2);
      Integer lineNumber = parseInt(parts.get(3));
      String message = parts.get(4);
      violations.add( //
          violationBuilder() //
              .setParser(SBTSCALAC) //
              .setStartLine(lineNumber) //
              .setFile(fileName) //
              .setSeverity(toSeverity(severity)) //
              .setMessage(message) //
              .build() //
          );
    }
    return violations;
  }

  public SEVERITY toSeverity(String severity) {
    if ("error".equals(severity)) {
      return ERROR;
    }
    if ("warn".equals(severity)) {
      return WARN;
    }
    return INFO;
  }
}
