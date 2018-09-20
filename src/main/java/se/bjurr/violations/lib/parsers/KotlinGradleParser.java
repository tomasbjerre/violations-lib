package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.KOTLINGRADLE;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class KotlinGradleParser implements ViolationsParser {
  @Override
  public List<Violation> parseReportOutput(final String string) throws Exception {
    List<Violation> violations = new ArrayList<>();
    List<List<String>> partsPerLine =
        getLines(string, "([^:])+?:([^:]*)[^\\d]+?(\\d+?)[^\\d]+?(\\d+?)[^:]+?:(.*)");
    for (List<String> parts : partsPerLine) {
      String severity = parts.get(1).trim();
      String filename = parts.get(2).trim();
      Integer line = parseInt(parts.get(3));
      Integer column = parseInt(parts.get(4));
      String message = parts.get(5).trim();
      violations.add( //
          violationBuilder() //
              .setParser(KOTLINGRADLE) //
              .setStartLine(line) //
              .setColumn(column) //
              .setFile(filename) //
              .setSeverity(toSeverity(severity)) //
              .setMessage(message) //
              .build() //
          );
    }
    return violations;
  }

  public SEVERITY toSeverity(final String severity) {
    if (severity.equalsIgnoreCase("e")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("w")) {
      return WARN;
    }
    return INFO;
  }
}
