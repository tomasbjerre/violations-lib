package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.reports.Reporter.ANDROIDLINT;

import java.util.ArrayList;
import java.util.List;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.Optional;

public class AndroidLintParser implements ViolationsParser {

 @Override
 public List<Violation> parseReportOutput(String string) throws Exception {
  List<Violation> violations = new ArrayList<>();
  List<String> issues = getChunks(string, "<issue", "</issue>");
  for (String issueChunk : issues) {
   /**
    * Only ever going to be one location.
    */
   String location = getChunks(issueChunk, "<location", "/>").get(0);

   String filename = getAttribute(location, "file");
   Optional<Integer> line = findIntegerAttribute(location, "line");
   Optional<Integer> charAttrib = findIntegerAttribute(location, "column");
   String severity = getAttribute(issueChunk, "severity");

   String id = getAttribute(issueChunk, "id");
   String message = getAttribute(issueChunk, "message");
   String summary = getAttribute(issueChunk, "summary").trim();
   String explanation = getAttribute(issueChunk, "explanation");

   String rule = getAttribute(issueChunk, "category");

   violations.add(//
     violationBuilder()//
       .setReporter(ANDROIDLINT)//
       .setStartLine(line.or(0))//
       .setColumn(charAttrib.orNull())//
       .setFile(filename)//
       .setSeverity(toSeverity(severity))//
       .setRule(rule)//
       .setMessage(id + ": " + summary + "\n" + message + "\n" + explanation)//
       .build()//
   );

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
