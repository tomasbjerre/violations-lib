package se.bjurr.violations.lib.parsers;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.collect.ImmutableMap.of;
import static com.google.common.collect.Lists.newArrayList;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getContent;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerContent;
import static se.bjurr.violations.lib.reports.Reporter.PITEST;

import java.io.File;
import java.util.List;

import se.bjurr.violations.lib.model.Violation;

import com.google.common.io.Files;

public class PiTestParser implements ViolationsParser {

 @Override
 public List<Violation> parseFile(File file) throws Exception {
  String string = Files.toString(file, UTF_8);
  List<Violation> violations = newArrayList();
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
       .setSpecifics(//
         of(//
           "detected", detected,//
           "mutatedMethod", mutatedMethod,//
           "mutatedClass", mutatedClass,//
           "status", status,//
           "methodDescription", methodDescription))//
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