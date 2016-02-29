package se.bjurr.violations.lib.parsers;

import static com.google.common.collect.Lists.newArrayList;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;

import java.io.File;
import java.util.List;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class PMDParser extends ViolationsParser {

 @Override
 public List<Violation> parseFile(File file) throws Exception {
  String string = Files.toString(file, Charsets.UTF_8);
  List<Violation> violations = newArrayList();
  List<String> files = getChunks(string, "<file", "</file>");
  for (String fileChunk : files) {
   String filename = getAttribute(fileChunk, "name");
   List<String> violationsChunks = getChunks(fileChunk, "<violation", "</violation>");
   for (String violationChunk : violationsChunks) {
    Integer beginLine = getIntegerAttribute(violationChunk, "beginline");
    Integer endLine = getIntegerAttribute(violationChunk, "endline");
    Integer beginColumn = getIntegerAttribute(violationChunk, "begincolumn");
    String rule = getAttribute(violationChunk, "rule").trim();
    String ruleSet = getAttribute(violationChunk, "ruleset").trim();
    String externalInfoUrl = getAttribute(violationChunk, "externalInfoUrl").trim();
    Integer priority = getIntegerAttribute(violationChunk, "priority");
    SEVERITY severity = toSeverity(priority);

    violations.add(//
      violationBuilder()//
        .setStartLine(beginLine)//
        .setEndLine(endLine)//
        .setColumn(beginColumn)//
        .setFile(filename)//
        .setSeverity(severity)//
        .setRule(rule)//
        .setMessage(ruleSet + " " + externalInfoUrl)//
        .build()//
      );
   }
  }
  return violations;
 }

 public SEVERITY toSeverity(Integer priority) {
  if (priority < 3) {
   return ERROR;
  }
  if (priority < 5) {
   return WARN;
  }
  return INFO;
 }
}
