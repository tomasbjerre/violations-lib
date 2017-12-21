package se.bjurr.violations.lib.parsers;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.KLOCWORK;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class KlocworkParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    try (InputStream input = new ByteArrayInputStream(string.getBytes())) {

      final XMLInputFactory factory = XMLInputFactory.newInstance();
      final XMLStreamReader xmlr = factory.createXMLStreamReader(input);

      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("problem")) {
            violations.add(parseBug(xmlr));
          }
        }
      }
    }
    return violations;
  }

  private Violation parseBug(XMLStreamReader xmlr) throws XMLStreamException {
    String file = null;
    String message = null;
    String code = null;
    Integer severitylevel = null;
    String method = null;
    String url = null;
    while (xmlr.hasNext()) {
      final int eventType = xmlr.next();
      if (eventType == END_ELEMENT && xmlr.getLocalName().equalsIgnoreCase("problem")) {
        break;
      }
      if (eventType != START_ELEMENT) {
        continue;
      }
      if (xmlr.getLocalName().equalsIgnoreCase("file")) {
        file = xmlr.getElementText();
      }
      if (xmlr.getLocalName().equalsIgnoreCase("message")) {
        message = xmlr.getElementText();
      }
      if (xmlr.getLocalName().equalsIgnoreCase("code")) {
        code = xmlr.getElementText();
      }
      if (xmlr.getLocalName().equalsIgnoreCase("severitylevel")) {
        severitylevel = Integer.parseInt(xmlr.getElementText());
      }
      if (xmlr.getLocalName().equalsIgnoreCase("method")) {
        method = xmlr.getElementText();
      }
      if (xmlr.getLocalName().equalsIgnoreCase("url")) {
        url = xmlr.getElementText();
      }
    }
    return violationBuilder() //
        .setParser(KLOCWORK) //
        .setFile(file) //
        .setMessage("In method " + method + ". " + message + " " + url) //
        .setRule(code) //
        .setSeverity(getSeverity(severitylevel)) //
        .setStartLine(1) //
        .build();
  }

  private SEVERITY getSeverity(Integer from) {
    if (from <= 2) {
      return ERROR;
    }
    if (from <= 3) {
      return WARN;
    }
    return INFO;
  }
}
