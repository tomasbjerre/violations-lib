package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.ZPTLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;

public class ZPTLintParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    for (final List<String> parts :
        getLines(string, "[ ]+Error in: (.*)  (.*)  , at line (\\d+).*")) {
      if (parts.size() < 3) {
        continue;
      }
      final Integer lineInFile = Integer.parseInt(parts.get(3));
      final String message = parts.get(2);
      final String fileName = parts.get(1);
      final Violation violation =
          violationBuilder() //
              .setParser(ZPTLINT) //
              .setFile(fileName) //
              .setMessage(message) //
              .setRule("ZPT") //
              .setSeverity(ERROR) //
              .setStartLine(lineInFile) //
              .build();
      violations.add(violation);
    }
    return violations;
  }
}
