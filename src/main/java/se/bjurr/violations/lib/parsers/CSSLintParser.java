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
import com.google.common.base.Optional;
import com.google.common.io.Files;

public class CSSLintParser extends ViolationsParser {

 @Override
 public List<Violation> parseFile(File file) throws Exception {
  String string = Files.toString(file, Charsets.UTF_8);
  List<Violation> violations = newArrayList();
  List<String> files = getChunks(string, "<file", "</file>");
  for (String fileChunk : files) {
   String filename = getAttribute(fileChunk, "name");
   List<String> issues = getChunks(fileChunk, "<issue", "/>");
   for (String errorChunk : issues) {
    Integer line = getIntegerAttribute(errorChunk, "line");
    Optional<Integer> charAttrib = findIntegerAttribute(errorChunk, "char");
    String severity = getAttribute(errorChunk, "severity");

    String message = getAttribute(errorChunk, "reason");
    String evidence = getAttribute(errorChunk, "evidence").trim();
    violations.add(//
      violationBuilder()//
        .setStartLine(line)//
        .setColumn(charAttrib.orNull())//
        .setFile(filename)//
        .setSeverity(toSeverity(severity))//
        .setMessage(message + ": " + evidence)//
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
