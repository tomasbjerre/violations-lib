package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.PCLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getParts;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class PCLintParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    List<Violation> violations = new ArrayList<>();
    List<String> lines = getLines(string);
    for (String line : lines) {
      List<String> parts =
          getParts(
              line,
              "^(.+)\\(",
              "^([\\d]+)\\): ",
              "^(Error|Warning|Info|Note) ",
              "^([\\d]+): ",
              "^(.*)$");
      if (parts.isEmpty()) {
        continue;
      }
      String filename = parts.get(0);
      Integer lineNumber = parseInt(parts.get(1));
      SEVERITY severity = toSeverity(parts.get(2));
      String rule = parts.get(3);
      String message = parts.get(4);
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
    return violations;
  }

  public SEVERITY toSeverity(String severity) {
    if (severity.equals("Error")) {
      return ERROR;
    }
    if (severity.equals("Warning")) {
      return WARN;
    }
    return INFO;
  }
}
