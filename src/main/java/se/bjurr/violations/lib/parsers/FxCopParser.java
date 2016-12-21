package se.bjurr.violations.lib.parsers;

import static java.util.logging.Level.FINE;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerAttribute;
import static se.bjurr.violations.lib.reports.Reporter.FXCOP;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.Optional;

public class FxCopParser implements ViolationsParser {
 private static Logger LOG = Logger.getLogger(FxCopParser.class.getSimpleName());

 @Override
 public List<Violation> parseReportOutput(String string) throws Exception {
  List<Violation> violations = new ArrayList<>();

  try (InputStream input = new ByteArrayInputStream(string.getBytes())) {

   XMLInputFactory factory = XMLInputFactory.newInstance();
   XMLStreamReader xmlr = factory.createXMLStreamReader(input);

   String targetName = null;
   String typeName = null;
   String classname = null;
   while (xmlr.hasNext()) {
    int eventType = xmlr.next();
    if (eventType == START_ELEMENT) {
     if (xmlr.getLocalName().equals("Target")) {
      targetName = getAttribute(xmlr, "Name").replaceAll("\\\\", "/");
     }
     if (xmlr.getLocalName().equals("Message")) {
      typeName = getAttribute(xmlr, "TypeName");
     }
     if (xmlr.getLocalName().equals("Type")) {
      classname = getAttribute(xmlr, "Name");
     }
     if (xmlr.getLocalName().equals("Issue")) {
      String level = getAttribute(xmlr, "Level");
      Optional<String> path = ViolationParserUtils.findAttribute(xmlr, "Path");
      if (!path.isPresent()) {
       LOG.log(FINE, "Ignoring project level issue");
       continue;
      }
      String fileName = getAttribute(xmlr, "File");
      Integer line = getIntegerAttribute(xmlr, "Line");
      String message = xmlr.getElementText().replaceAll("\\s+", " ");

      String filename = path.get() + "/" + fileName;
      SEVERITY severity = toSeverity(level);
      violations.add(//
        violationBuilder()//
          .setReporter(FXCOP)//
          .setMessage(message)//
          .setFile(filename)//
          .setStartLine(line)//
          .setRule(typeName)//
          .setSeverity(severity)//
          .setSource(classname)//
          .setSpecific("TARGET_NAME", targetName)//
          .build()//
      );
     }
    }
   }
  }
  return violations;
 }

 private SEVERITY toSeverity(String issueLevel) {
  if (issueLevel.contains("CriticalError")) {
   return ERROR;
  } else if (issueLevel.contains("Error")) {
   return ERROR;
  } else if (issueLevel.contains("CriticalWarning")) {
   return WARN;
  } else if (issueLevel.contains("Warning")) {
   return WARN;
  }
  return INFO;
 }
}
