package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerAttribute;
import static se.bjurr.violations.lib.reports.Reporter.CHECKSTYLE;

import java.util.ArrayList;
import java.util.List;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.Optional;

public class CheckStyleParser implements ViolationsParser {

 @Override
 public List<Violation> parseFile(String string) throws Exception {
  List<Violation> violations = new ArrayList<>();
  List<String> files = getChunks(string, "<file", "</file>");
  for (String fileChunk : files) {
   String filename = getAttribute(fileChunk, "name");
   List<String> errors = getChunks(fileChunk, "<error", "/>");
   for (String errorChunk : errors) {
    Integer line = getIntegerAttribute(errorChunk, "line");
    Optional<Integer> column = findIntegerAttribute(errorChunk, "column");
    String severity = getAttribute(errorChunk, "severity");
    String message = getAttribute(errorChunk, "message");
    String rule = getAttribute(errorChunk, "source");
    violations.add(//
      violationBuilder()//
        .setReporter(CHECKSTYLE)//
        .setStartLine(line)//
        .setColumn(column.orNull())//
        .setFile(filename)//
        .setSeverity(toSeverity(severity))//
        .setMessage(message)//
        .setRule(rule)//
        .build()//
    );
   }
  }
  return violations;
 }

 public SEVERITY toSeverity(String severity) {
  if (severity.equalsIgnoreCase("ERROR")) {
   return ERROR;
  }
  if (severity.equalsIgnoreCase("WARNING")) {
   return WARN;
  }
  return INFO;
 }

}
