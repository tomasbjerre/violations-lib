package se.bjurr.violations.lib.parsers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Reporter.RESHARPER;

import java.io.File;
import java.util.List;
import java.util.Map;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ResharperParser extends ViolationsParser {

 @Override
 public List<Violation> parseFile(File file) throws Exception {
  String string = Files.toString(file, Charsets.UTF_8);
  List<Violation> violations = newArrayList();
  List<String> issueTypeChunks = getChunks(string, "<IssueType ", "/>");
  Map<String, Map<String, String>> issueTypesPerTypeId = newHashMap();
  for (String issueTypesChunk : issueTypeChunks) {
   Map<String, String> issueType = newHashMap();
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
