package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.PYDOCSTYLE;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getParts;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class PyDocStyleParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(final String string, ViolationsLogger violationsLogger)
      throws Exception {
    final List<Violation> violations = new ArrayList<>();
    boolean fileLine = true;
    final List<String> lines = ViolationParserUtils.getLines(string);
    String filename = null;
    Integer line = null;
    for (final String inputLine : lines) {
      try {
        if (fileLine) {
          final List<String> parts = getParts(inputLine, "([^:]*)", "(\\d+)");
          filename = parts.get(0);
          if (!filename.endsWith(".py")) {
            continue;
          }
          line = parseInt(parts.get(1));
        } else {
          final List<String> parts = getParts(inputLine, "([^:]*)", ":(.*)");
          if (parts.size() != 2) {
            continue;
          }
          final String rule = parts.get(0);
          final String message = parts.get(1);

          violations.add( //
              violationBuilder() //
                  .setParser(PYDOCSTYLE) //
                  .setStartLine(line) //
                  .setFile(filename) //
                  .setRule(rule) //
                  .setSeverity(ERROR) //
                  .setMessage(message) //
                  .build() //
              );
        }
      } catch (IndexOutOfBoundsException | NumberFormatException e) {
        continue;
      }
      fileLine = !fileLine;
    }
    return violations;
  }
}
