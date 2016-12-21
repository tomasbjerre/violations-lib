package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getLines;
import static se.bjurr.violations.lib.reports.Reporter.PYLINT;

import java.util.ArrayList;
import java.util.List;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

/**
 * PyLint.<br>
 * <code>
 * pylint --output-format=parseable --reports=n voluptuous
 * </code>
 */
public class PyLintParser implements ViolationsParser {

 @Override
 public List<Violation> parseReportOutput(String string) throws Exception {
  final List<Violation> violations = new ArrayList<>();
  final List<List<String>> partsPerLine = getLines(string,
    "([^:]*):(\\d+): \\[(\\D)(\\d*)\\(([^\\]]*)\\), ([^\\]]*)] (.*)");
  for (final List<String> parts : partsPerLine) {
   final String filename = parts.get(1);
   final Integer line = parseInt(parts.get(2));
   final String severity = parts.get(3);
   final String ruleCode = parts.get(4);
   final String rule = parts.get(5);
   final String method = parts.get(6);
   final String message = parts.get(7);
   violations.add(//
     violationBuilder()//
       .setReporter(PYLINT)//
       .setStartLine(line)//
       .setFile(filename)//
       .setRule(severity + ruleCode + "(" + rule + ")")//
       .setSeverity(toSeverity(severity))//
       .setMessage(message)//
       .setSpecific("method", method)//
       .build()//
   );
  }
  return violations;
 }

 /**
  * <pre>
  * The different message types are:
  * (C) convention, for programming standard violation
  * (R) refactor, for bad code smell
  * (W) warning, for python specific problems
  * (E) error, for much probably bugs in the code
  * (F) fatal, if an error occured which prevented pylint from doing
  *     further processing.
  * </pre>
  */
 public SEVERITY toSeverity(final String severity) {
  if (severity.equalsIgnoreCase("E") || severity.equalsIgnoreCase("F")) {
   return ERROR;
  }
  if (severity.equalsIgnoreCase("W")) {
   return WARN;
  }
  return INFO;
 }

}