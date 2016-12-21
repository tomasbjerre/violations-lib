package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getLines;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getParts;
import static se.bjurr.violations.lib.reports.Reporter.CPPLINT;

import java.util.ArrayList;
import java.util.List;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class CppLintParser implements ViolationsParser {

 @Override
 public List<Violation> parseReportOutput(String string) throws Exception {
  List<Violation> violations = new ArrayList<>();
  List<String> lines = getLines(string);
  for (String line : lines) {
   List<String> parts = getParts(line, "\\[([^\\]]*)\\]$", "\\[([^\\]]*)\\]$", "^([^:]*):", "^([^:]*):", "(.*)");
   if (parts.isEmpty()) {
    continue; // Happens for the line "Done processing cpp/test.cpp"
   }
   Integer severity = parseInt(parts.get(0));
   String rule = parts.get(1);
   String filename = parts.get(2);
   Integer lineNumber = parseInt(parts.get(3));
   String message = parts.get(4);
   violations.add(//
     violationBuilder()//
       .setReporter(CPPLINT)//
       .setStartLine(lineNumber)//
       .setFile(filename)//
       .setRule(rule)//
       .setSeverity(toSeverity(severity))//
       .setMessage(message)//
       .build()//
   );
  }
  return violations;
 }

 public SEVERITY toSeverity(Integer severity) {
  if (severity >= 5) {
   return ERROR;
  }
  if (severity >= 3) {
   return WARN;
  }
  return INFO;
 }

}
