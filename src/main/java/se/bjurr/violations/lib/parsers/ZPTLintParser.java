package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.ZPTLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.Violation;

public class ZPTLintParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    List<Violation> violations = new ArrayList<>();
    for (List<String> parts : getLines(string, "[ ]+Error in: (.*)  (.*)  , at line (\\d+).*")) {
      if (parts.size() < 3) {
        continue;
      }
      Integer lineInFile = Integer.parseInt(parts.get(3));
      String message = parts.get(2);
      String fileName = parts.get(1);
      Violation violation =
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
