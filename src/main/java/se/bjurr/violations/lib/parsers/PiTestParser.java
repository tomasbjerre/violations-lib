package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getContent;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerContent;
import static se.bjurr.violations.lib.reports.Reporter.PITEST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.bjurr.violations.lib.model.Violation;

public class PiTestParser implements ViolationsParser {

 @Override
 public List<Violation> parseFile(String string) throws Exception {
  List<Violation> violations = new ArrayList<>();
  String mutations = getContent(string, "mutations");
  List<String> mutationChunks = getChunks(mutations, "<mutation", "</mutation>");
  for (String mutationChunk : mutationChunks) {
   String mutatedClass = getContent(mutationChunk, "mutatedClass");
   String sourceFile = getContent(mutationChunk, "sourceFile");
   String filename = toFilename(mutatedClass, sourceFile);
   String status = getAttribute(mutationChunk, "status");
   String detected = getAttribute(mutationChunk, "detected");
   String mutatedMethod = getContent(mutationChunk, "mutatedMethod");
   String methodDescription = getContent(mutationChunk, "methodDescription");
   String mutator = getContent(mutationChunk, "mutator");
   String message = status + ", " + mutator + ", " + methodDescription;
   Integer startLine = getIntegerContent(mutationChunk, "lineNumber");
   Integer index = getIntegerContent(mutationChunk, "index");
   Map<String, String> specifics = new HashMap<>();
   specifics.put("detected", detected);
   specifics.put("mutatedMethod", mutatedMethod);
   specifics.put("mutatedClass", mutatedClass);
   specifics.put("status", status);
   specifics.put("methodDescription", methodDescription);
   violations.add(//
     violationBuilder()//
       .setRule(mutator)//
       .setSource(sourceFile)//
       .setReporter(PITEST)//
       .setStartLine(startLine)//
       .setColumn(index)//
       .setFile(filename)//
       .setSeverity(WARN)//
       .setMessage(message)//
       .setSpecifics(specifics)//
       .build()//
   );
  }
  return violations;
 }

 /**
  * Use package from mutadedClass and assume sourceFile is in that package.
  */
 private String toFilename(String mutatedClass, String sourceFile) {
  return mutatedClass.substring(0, mutatedClass.lastIndexOf(".")).replaceAll("\\.", "/") + "/" + sourceFile;
 }
}