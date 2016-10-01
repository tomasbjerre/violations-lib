package se.bjurr.violations.lib.parsers;

import static com.google.common.collect.Lists.newArrayList;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerAttribute;
import static se.bjurr.violations.lib.reports.Reporter.STYLECOP;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class StyleCopParser implements ViolationsParser {

 @Override
 public List<Violation> parseFile(File file) throws Exception {
  List<Violation> violations = newArrayList();

  try (InputStream input = new FileInputStream(file)) {

   XMLInputFactory factory = XMLInputFactory.newInstance();
   XMLStreamReader xmlr = factory.createXMLStreamReader(input);

   while (xmlr.hasNext()) {
    int eventType = xmlr.next();
    if (eventType == START_ELEMENT) {
     if (xmlr.getLocalName().equals("Violation")) {
      String section = getAttribute(xmlr, "Section");
      String source = getAttribute(xmlr, "Source");
      String ruleNamespace = getAttribute(xmlr, "RuleNamespace");
      String rule = getAttribute(xmlr, "Rule");
      String ruleId = getAttribute(xmlr, "RuleId");
      Integer lineNumber = getIntegerAttribute(xmlr, "LineNumber");
      String message = xmlr.getElementText().replaceAll("\\s+", " ");
      SEVERITY severity = INFO;
      String filename = toFile(source);
      violations.add(//
        violationBuilder()//
          .setReporter(STYLECOP)//
          .setMessage(message)//
          .setFile(filename)//
          .setStartLine(lineNumber)//
          .setRule(rule)//
          .setSeverity(severity)//
          .setSource(source)//
          .setSpecific("section", section)//
          .setSpecific("source", source)//
          .setSpecific("ruleNamespace", ruleNamespace)//
          .setSpecific("rule", rule)//
          .setSpecific("ruleId", ruleId)//
          .build()//
      );
     }
    }
   }
  }
  return violations;
 }

 private String toFile(String source) {
  return source.replaceAll("\\.", "/");
 }
}
