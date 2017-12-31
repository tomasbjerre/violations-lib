package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CPPLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getParts;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class PerlCriticParser implements ViolationsParser {
  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    List<Violation> violations = new ArrayList<>();
    List<String> lines = getLines(string);
    for (String line : lines) {
      List<String> parts =
          getParts(
              line,
              "\\(Severity: (\\d*)\\)$",
              "^([^:]*):",
              "^(.+?) at line ",
              "^(\\d*), ",
              "column (\\d*)\\.  ",
              "(.*)");
      if (parts.isEmpty()) {
        continue;
      }
      Integer severity = parseInt(parts.get(0));
      String filename = parts.get(1);
      String message = parts.get(2);
      Integer lineNumber = parseInt(parts.get(3));
      Integer columnNumber = parseInt(parts.get(4));
      String rule = parts.get(5);

      violations.add( //
          violationBuilder() //
              .setParser(CPPLINT) //
              .setStartLine(lineNumber) //
              .setColumn(columnNumber) //
              .setFile(filename) //
              .setRule(rule) //
              .setSeverity(toSeverity(severity)) //
              .setMessage(message) //
              .build() //
          );
    }
    return violations;
  }

  public SEVERITY toSeverity(Integer severity) {
    if (severity >= 4) {
      return ERROR;
    }
    if (severity >= 3) {
      return WARN;
    }
    return INFO;
  }
}
