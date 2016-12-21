package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerAttribute;
import static se.bjurr.violations.lib.reports.Reporter.JSHINT;

import java.util.ArrayList;
import java.util.List;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class JSHintParser implements ViolationsParser {
 @Override
 public List<Violation> parseReportOutput(String string) throws Exception {
  List<Violation> violations = new ArrayList<>();
  List<String> files = getChunks(string, "<file", "</file>");
  for (String fileChunk : files) {
   String filename = getAttribute(fileChunk, "name");
   List<String> issues = getChunks(fileChunk, "<issue", "/>");
   for (String issueChunk : issues) {
    Integer line = getIntegerAttribute(issueChunk, "line");
    Integer charAttrib = getIntegerAttribute(issueChunk, "char");
    String severity = getAttribute(issueChunk, "severity");
    String reason = getAttribute(issueChunk, "reason").trim();
    String evidence = getAttribute(issueChunk, "evidence").trim();
    String message = reason + ": " + evidence;

    violations.add(//
      violationBuilder()//
        .setReporter(JSHINT)//
        .setStartLine(line)//
        .setColumn(charAttrib)//
        .setFile(filename)//
        .setSeverity(toSeverity(severity))//
        .setMessage(message)//
        .build()//
    );
   }
  }
  return violations;
 }

 public SEVERITY toSeverity(String severity) {
  if (severity.equalsIgnoreCase("E")) {
   return ERROR;
  }
  if (severity.equalsIgnoreCase("W")) {
   return WARN;
  }
  return INFO;
 }
}
