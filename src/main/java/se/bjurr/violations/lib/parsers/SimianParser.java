package se.bjurr.violations.lib.parsers;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerAttribute;
import static se.bjurr.violations.lib.reports.Reporter.SIMIAN;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class SimianParser implements ViolationsParser {

 @Override
 public List<Violation> parseFile(String string) throws Exception {
  List<Violation> violations = new ArrayList<>();

  try (InputStream input = new ByteArrayInputStream(string.getBytes())) {

   XMLInputFactory factory = XMLInputFactory.newInstance();
   XMLStreamReader xmlr = factory.createXMLStreamReader(input);

   String sourceFile = null;
   Integer lineCount = null;
   Integer startLineNumber = null;
   Integer endLineNumber = null;
   while (xmlr.hasNext()) {
    int eventType = xmlr.next();
    if (eventType == START_ELEMENT) {
     if (xmlr.getLocalName().equals("set")) {
      lineCount = getIntegerAttribute(xmlr, "lineCount");
     }
     if (xmlr.getLocalName().equals("block")) {
      sourceFile = getAttribute(xmlr, "sourceFile");
      startLineNumber = getIntegerAttribute(xmlr, "startLineNumber");
      endLineNumber = getIntegerAttribute(xmlr, "endLineNumber");

      Violation violation = violationBuilder()//
        .setReporter(SIMIAN)//
        .setFile(sourceFile)//
        .setMessage("Duplication")//
        .setRule("DUPLICATION")//
        .setSeverity(toSeverity(lineCount))//
        .setStartLine(startLineNumber)//
        .setEndLine(endLineNumber)//
        .build();
      violations.add(violation);
     }
    }
   }
  }
  return violations;
 }

 private SEVERITY toSeverity(Integer lineCount) {
  if (lineCount < 10) {
   return INFO;
  }
  if (lineCount < 50) {
   return WARN;
  }
  return ERROR;
 }
}
