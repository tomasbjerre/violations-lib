package se.bjurr.violations.lib.parsers;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.JCREPORT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class JCReportParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    final List<Violation> violations = new ArrayList<>();

    try (InputStream input = new ByteArrayInputStream(string.getBytes("UTF-8"))) {

      final XMLInputFactory factory = XMLInputFactory.newInstance();
      final XMLStreamReader xmlr = factory.createXMLStreamReader(input);

      String name = null;
      String findingType = null;
      Integer line = null;
      String message = null;
      String origin = null;
      String severity = null;
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("file")) {
            name = getAttribute(xmlr, "name");
          }
          if (xmlr.getLocalName().equalsIgnoreCase("item")) {
            findingType = getAttribute(xmlr, "finding-type");
            line = getIntegerAttribute(xmlr, "line");
            message = getAttribute(xmlr, "message");
            origin = getAttribute(xmlr, "origin");
            severity = getAttribute(xmlr, "severity");
            final Violation violation =
                violationBuilder() //
                    .setParser(JCREPORT) //
                    .setFile(name) //
                    .setMessage(message) //
                    .setRule(findingType + "(" + origin + ")") //
                    .setSeverity(toSeverity(severity)) //
                    .setStartLine(line) //
                    .build();
            violations.add(violation);
          }
        }
      }
    }
    return violations;
  }

  private SEVERITY toSeverity(String severity) {
    if (severity.equalsIgnoreCase("error")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("cpd")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("warning")) {
      return WARN;
    }
    if (severity.equalsIgnoreCase("design")) {
      return WARN;
    }
    if (severity.equalsIgnoreCase("code-style")) {
      return INFO;
    }
    return INFO;
  }
}
