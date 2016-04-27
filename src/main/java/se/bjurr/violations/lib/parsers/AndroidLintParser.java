package se.bjurr.violations.lib.parsers;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.collect.Lists.newArrayList;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Reporter.ANDROIDLINT;

import java.io.File;
import java.util.List;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

import com.google.common.base.Optional;
import com.google.common.io.Files;

public class AndroidLintParser extends ViolationsParser {

 @Override
 public List<Violation> parseFile(File file) throws Exception {
  String string = Files.toString(file, UTF_8);
  List<Violation> violations = newArrayList();
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
