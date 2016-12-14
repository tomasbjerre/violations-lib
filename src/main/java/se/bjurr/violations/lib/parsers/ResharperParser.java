package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.reports.Reporter.RESHARPER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class ResharperParser implements ViolationsParser {

 @Override
 public List<Violation> parseFile(String string) throws Exception {
  List<Violation> violations = new ArrayList<>();
  List<String> issueTypeChunks = getChunks(string, "<IssueType ", "/>");
  Map<String, Map<String, String>> issueTypesPerTypeId = new HashMap<>();
  for (String issueTypesChunk : issueTypeChunks) {
   Map<String, String> issueType = new HashMap<>();
   String id = getAttribute(issueTypesChunk, "Id");
   issueType.put("category", getAttribute(issueTypesChunk, "Category"));
   issueType.put("description", getAttribute(issueTypesChunk, "Description"));
   issueType.put("severity", getAttribute(issueTypesChunk, "Severity"));
   issueTypesPerTypeId.put(id, issueType);
  }

  List<String> issueChunks = getChunks(string, "<Issue ", "/>");
  for (String issueChunk : issueChunks) {
   String typeId = getAttribute(issueChunk, "TypeId");
   String filename = getAttribute(issueChunk, "File");
   String message = getAttribute(issueChunk, "Message") + ". " + issueTypesPerTypeId.get(typeId).get("category") + ". "
     + issueTypesPerTypeId.get(typeId).get("description");
   Integer line = findIntegerAttribute(issueChunk, "Line").or(0);
   String severity = issueTypesPerTypeId.get(typeId).get("severity");
   violations.add(//
     violationBuilder()//
       .setReporter(RESHARPER)//
       .setStartLine(line)//
       .setFile(filename)//
       .setSeverity(toSeverity(severity))//
       .setMessage(message)//
       .setRule(typeId)//
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
