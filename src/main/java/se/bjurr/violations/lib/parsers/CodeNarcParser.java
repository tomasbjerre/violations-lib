package se.bjurr.violations.lib.parsers;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerAttribute;
import static se.bjurr.violations.lib.reports.Reporter.CODENARC;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class CodeNarcParser implements ViolationsParser {

 private SEVERITY getSeverity(Integer from) {
  if (from == 1) {
   return ERROR;
  }
  if (from == 2) {
   return WARN;
  }
  return INFO;
 }

 @Override
 public List<Violation> parseReportOutput(String string) throws Exception {
  List<Violation> violations = new ArrayList<>();
  Map<String, String> rules = new HashMap<>();
  try (InputStream input = new ByteArrayInputStream(string.getBytes())) {
   XMLInputFactory factory = XMLInputFactory.newInstance();
   XMLStreamReader xmlr = factory.createXMLStreamReader(input);
   String name = null;
   String description = null;
   while (xmlr.hasNext()) {
    int eventType = xmlr.next();
    if (eventType == START_ELEMENT) {
     if (xmlr.getLocalName().equals("Rule")) {
      name = getAttribute(xmlr, "name");
     }
     if (xmlr.getLocalName().equals("Description")) {
      description = xmlr.getElementText().trim();
      rules.put(name, description);
     }
    }
   }
  }

  try (InputStream input = new ByteArrayInputStream(string.getBytes())) {

   XMLInputFactory factory = XMLInputFactory.newInstance();
   XMLStreamReader xmlr = factory.createXMLStreamReader(input);

   String path = null;
   String name = null;
   String ruleName = null;
   Integer priority = null;
   Integer lineNumber = null;
   while (xmlr.hasNext()) {
    int eventType = xmlr.next();
    if (eventType == START_ELEMENT) {
     if (xmlr.getLocalName().equals("Package")) {
      path = getAttribute(xmlr, "path");

     }
     if (xmlr.getLocalName().equals("File")) {
      name = getAttribute(xmlr, "name");
     }
     if (xmlr.getLocalName().equals("Violation")) {
      ruleName = getAttribute(xmlr, "ruleName");
      priority = getIntegerAttribute(xmlr, "priority");
      String lineNumberString = getAttribute(xmlr, "lineNumber");
      lineNumber = 1;
      if (lineNumberString != null && !lineNumberString.isEmpty()) {
        lineNumber = Integer.parseInt(lineNumberString);
      }
      String message = rules.get(ruleName);
      if (message == null) {
       message = ruleName;
      }
      Violation violation = violationBuilder()//
        .setReporter(CODENARC)//
        .setFile(path + "/" + name)//
        .setMessage(message)//
        .setRule(ruleName)//
        .setSeverity(getSeverity(priority))//
        .setStartLine(lineNumber)//
        .build();
      violations.add(violation);
     }
    }
   }
  }
  return violations;
 }
}
