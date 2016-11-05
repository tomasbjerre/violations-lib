package se.bjurr.violations.lib.parsers;

import static com.google.common.collect.Lists.newArrayList;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerAttribute;
import static se.bjurr.violations.lib.reports.Reporter.JCREPORT;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class JCReportParser implements ViolationsParser {

 @Override
 public List<Violation> parseFile(String string) throws Exception {
  List<Violation> violations = newArrayList();

  try (InputStream input = new ByteArrayInputStream(string.getBytes())) {

   XMLInputFactory factory = XMLInputFactory.newInstance();
   XMLStreamReader xmlr = factory.createXMLStreamReader(input);

   String name = null;
   String findingType = null;
   Integer line = null;
   String message = null;
   String origin = null;
   String severity = null;
   while (xmlr.hasNext()) {
    int eventType = xmlr.next();
    if (eventType == START_ELEMENT) {
     if (xmlr.getLocalName().equals("file")) {
      name = getAttribute(xmlr, "name");
     }
     if (xmlr.getLocalName().equals("item")) {
      findingType = getAttribute(xmlr, "finding-type");
      line = getIntegerAttribute(xmlr, "line");
      message = getAttribute(xmlr, "message");
      origin = getAttribute(xmlr, "origin");
      severity = getAttribute(xmlr, "severity");
      Violation violation = violationBuilder()//
        .setReporter(JCREPORT)//
        .setFile(name)//
        .setMessage(message)//
        .setRule(findingType + "(" + origin + ")")//
        .setSeverity(toSeverity(severity))//
        .setStartLine(line)//
        .build();
      violations.add(violation);
     }
    }
   }
  }
  return violations;
 }

 private SEVERITY toSeverity(String severity) {
  if (severity.equals("error")) {
   return ERROR;
  }
  if (severity.equals("cpd")) {
   return ERROR;
  }
  if (severity.equals("warning")) {
   return WARN;
  }
  if (severity.equals("design")) {
   return WARN;
  }
  if (severity.equals("code-style")) {
   return INFO;
  }
  return INFO;
 }
}
