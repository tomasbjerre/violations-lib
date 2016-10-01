package se.bjurr.violations.lib.parsers;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getLines;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getParts;
import static se.bjurr.violations.lib.reports.Reporter.XMLLINT;

import java.io.File;
import java.util.List;

import se.bjurr.violations.lib.model.Violation;

import com.google.common.io.Files;

public class XMLLintParser implements ViolationsParser {

 @Override
 public List<Violation> parseFile(File file) throws Exception {
  String string = Files.toString(file, UTF_8);
  List<Violation> violations = newArrayList();
  List<String> lines = getLines(string);
  for (String line : lines) {
   List<String> parts = getParts(line, "^([^:]*):", "^([^:]*):", "^([^:]*):", "(.*)");
   if (parts.isEmpty()) {
    continue;
   }
   String filename = parts.get(0);
   Integer lineNumber = parseInt(parts.get(1));
   String rule = parts.get(2);
   String message = parts.get(3);
   violations.add(//
     violationBuilder()//
       .setReporter(XMLLINT)//
       .setStartLine(lineNumber)//
       .setFile(filename)//
       .setRule(rule)//
       .setSeverity(ERROR)//
       .setMessage(message)//
       .build()//
     );
  }
  return violations;
 }

}
