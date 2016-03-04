package se.bjurr.violations.lib.parsers;

import static com.google.common.collect.Lists.newArrayList;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Reporter.JSHINT;

import java.io.File;
import java.util.List;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class JSHintParser extends ViolationsParser {

 @Override
 public List<Violation> parseFile(File file) throws Exception {
  String string = Files.toString(file, Charsets.UTF_8);
  List<Violation> violations = newArrayList();
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
