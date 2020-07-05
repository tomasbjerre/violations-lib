package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.XMLLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getParts;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;

public class XMLLintParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    final List<String> lines = getLines(string);
    for (final String line : lines) {
      final List<String> parts = getParts(line, "^([^:]*):", "^([^:]*):", "^([^:]*):", "(.*)");
      if (parts.isEmpty()) {
        continue;
      }
      final String filename = parts.get(0);
      final Integer lineNumber = parseInt(parts.get(1));
      final String rule = parts.get(2);
      final String message = parts.get(3);
      violations.add( //
          violationBuilder() //
              .setParser(XMLLINT) //
              .setStartLine(lineNumber) //
              .setFile(filename) //
              .setRule(rule) //
              .setSeverity(ERROR) //
              .setMessage(message) //
              .build() //
          );
    }
    return violations;
  }
}
