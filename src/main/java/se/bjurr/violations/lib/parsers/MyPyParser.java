package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.MYPY;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class MyPyParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String reportContent) throws Exception {
    List<Violation> violations = new ArrayList<>();
    List<List<String>> partsPerLine = getLines(reportContent, "^(.*):(\\d+): (.*): (.*)$");
    for (List<String> parts : partsPerLine) {
      String fileName = parts.get(1);
      Integer lineNumber = 0;
      if (!parts.get(2).isEmpty()) {
        lineNumber = parseInt(parts.get(2));
      }
      String severity = parts.get(3);
      String message = parts.get(4);
      violations.add( //
          violationBuilder() //
              .setParser(MYPY) //
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
    if (severity.equalsIgnoreCase("error")) {
      return ERROR;
    }
    return INFO;
  }
}
