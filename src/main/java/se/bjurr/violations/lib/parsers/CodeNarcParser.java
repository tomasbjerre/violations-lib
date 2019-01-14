package se.bjurr.violations.lib.parsers;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CODENARC;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
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
    final List<Violation> violations = new ArrayList<>();

    final Map<String, String> rules = getRules(string);

    final String sourceDirectory = getSourceDirectory(string);

    try (InputStream input = new ByteArrayInputStream(string.getBytes("UTF-8"))) {

      final XMLInputFactory factory = XMLInputFactory.newInstance();
      final XMLStreamReader xmlr = factory.createXMLStreamReader(input);

      String path = null;
      String name = null;
      String ruleName = null;
      Integer priority = null;
      Integer lineNumber = null;
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("Package")) {
            path = getAttribute(xmlr, "path");
          }
          if (xmlr.getLocalName().equalsIgnoreCase("File")) {
            name = getAttribute(xmlr, "name");
          }
          if (xmlr.getLocalName().equalsIgnoreCase("Violation")) {
            ruleName = getAttribute(xmlr, "ruleName");
            priority = getIntegerAttribute(xmlr, "priority");
            final String lineNumberString = getAttribute(xmlr, "lineNumber");
            lineNumber = 1;
            if (!lineNumberString.isEmpty()) {
              lineNumber = Integer.parseInt(lineNumberString);
            }
            String message = rules.get(ruleName);
            if (message == null) {
              message = ruleName;
            }
            String fileString = null;
            if (sourceDirectory == null || sourceDirectory.isEmpty()) {
              fileString = path + "/" + name;
            } else {
              fileString = sourceDirectory + "/" + path + "/" + name;
            }
            final Violation violation =
                violationBuilder() //
                    .setParser(CODENARC) //
                    .setFile(fileString.replace("//", "/")) //
                    .setMessage(message) //
                    .setRule(ruleName) //
                    .setSeverity(getSeverity(priority)) //
                    .setStartLine(lineNumber) //
                    .build();
            violations.add(violation);
          }
        }
      }
    }
    return violations;
  }

  private String getSourceDirectory(final String string) throws Exception {
    try (InputStream input = new ByteArrayInputStream(string.getBytes("UTF-8"))) {
      final XMLInputFactory factory = XMLInputFactory.newInstance();
      final XMLStreamReader xmlr = factory.createXMLStreamReader(input);
      String name = null;
      String description = null;
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("SourceDirectory")) {
            return xmlr.getElementText().trim();
          }
        }
      }
    }
    return "";
  }

  private Map<String, String> getRules(final String string) throws IOException, XMLStreamException {
    final Map<String, String> rules = new HashMap<>();
    try (InputStream input = new ByteArrayInputStream(string.getBytes("UTF-8"))) {
      final XMLInputFactory factory = XMLInputFactory.newInstance();
      final XMLStreamReader xmlr = factory.createXMLStreamReader(input);
      String name = null;
      String description = null;
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("Rule")) {
            name = getAttribute(xmlr, "name");
          }
          if (xmlr.getLocalName().equalsIgnoreCase("Description")) {
            description = xmlr.getElementText().trim();
            rules.put(name, description);
          }
        }
      }
    }
    return rules;
  }
}
