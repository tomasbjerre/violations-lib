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
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class KlocworkParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(final String string, ViolationsLogger violationsLogger)
      throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    try (InputStream input = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8))) {

      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);

      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("problem")) {
            violations.add(this.parseBug(xmlr));
          }
        }
      }
    }
    return violations;
  }

  private Violation parseBug(final XMLStreamReader xmlr) throws XMLStreamException {
    String file = null;
    String message = null;
    String code = null;
    Integer severitylevel = null;
    String method = null;
    String url = null;
    Integer linenumber = 1;
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
      if (xmlr.getLocalName().equalsIgnoreCase("line")) {
        linenumber = Integer.parseInt(xmlr.getElementText());
      }
    }
    return violationBuilder() //
        .setParser(KLOCWORK) //
        .setFile(file) //
        .setMessage("In method " + method + ". " + message + " " + url) //
        .setRule(code) //
        .setSeverity(this.getSeverity(severitylevel)) //
        .setStartLine(linenumber) //
        .build();
  }

  private SEVERITY getSeverity(final Integer from) {
    if (from <= 2) {
      return ERROR;
    }
    if (from <= 3) {
      return WARN;
    }
    return INFO;
  }
}
