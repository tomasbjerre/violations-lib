package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.XMLLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getParts;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.Violation;

public class XMLLintParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    List<Violation> violations = new ArrayList<>();
    List<String> lines = getLines(string);
    for (String line : lines) {
      List<String> parts = getParts(line, "^([^:]*):", "^([^:]*):", "^([^:]*):", "(.*)");
      if (parts.isEmpty()) {
        continue;
      }
      String filename = parts.get(0);
      Integer lineNumber = parseInt(parts.get(1));
      String rule = parts.get(2);
      String message = parts.get(3);
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
