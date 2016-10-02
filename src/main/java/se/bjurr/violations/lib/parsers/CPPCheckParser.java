package se.bjurr.violations.lib.parsers;

import static com.google.common.collect.Lists.newArrayList;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerAttribute;
import static se.bjurr.violations.lib.reports.Reporter.CPPCHECK;

import java.io.File;
import java.util.List;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class CPPCheckParser implements ViolationsParser {

 @Override
 public List<Violation> parseFile(File file) throws Exception {
  String string = Files.toString(file, Charsets.UTF_8);
  List<Violation> violations = newArrayList();
  List<String> errorChunks = getChunks(string, "<error", "</error>");
  for (String errorChunk : errorChunks) {
   String severity = getAttribute(errorChunk, "severity");
   String msg = getAttribute(errorChunk, "msg");
   String verbose = getAttribute(errorChunk, "verbose");
   String id = getAttribute(errorChunk, "id");
   List<String> locationChunks = getChunks(errorChunk, "<location", "/>");
   for (String locationChunk : locationChunks) {
    Integer line = getIntegerAttribute(locationChunk, "line");
    String fileString = getAttribute(errorChunk, "file");
    violations.add(//
      violationBuilder()//
        .setReporter(CPPCHECK)//
        .setStartLine(line)//
        .setFile(fileString)//
        .setSeverity(toSeverity(severity))//
        .setMessage(msg + ". " + verbose)//
        .setRule(id)//
        .build()//
    );
   }
  }
  return violations;
 }

 public SEVERITY toSeverity(String severity) {
  if (severity.equalsIgnoreCase("error")) {
   return ERROR;
  }
  if (severity.equalsIgnoreCase("warning")) {
   return WARN;
  }
  return INFO;
 }

}
