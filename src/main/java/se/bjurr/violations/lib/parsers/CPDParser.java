package se.bjurr.violations.lib.parsers;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerAttribute;
import static se.bjurr.violations.lib.reports.Parser.CODENARC;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class CPDParser implements ViolationsParser {

  private SEVERITY getSeverity(Integer from) {
    if (from < 100) {
      return INFO;
    }
    if (from < 1000) {
      return WARN;
    }
    return ERROR;
  }

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    try (InputStream input = new ByteArrayInputStream(string.getBytes("UTF-8"))) {

      final XMLInputFactory factory = XMLInputFactory.newInstance();
      final XMLStreamReader xmlr = factory.createXMLStreamReader(input);

      final List<String> files = new ArrayList<>();
      final List<Integer> filesLine = new ArrayList<>();
      Integer tokens = null;
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("duplication")) {
            tokens = getIntegerAttribute(xmlr, "tokens");
          }
          if (xmlr.getLocalName().equalsIgnoreCase("file")) {
            files.add(getAttribute(xmlr, "path"));
            filesLine.add(getIntegerAttribute(xmlr, "line"));
          }
          if (xmlr.getLocalName().equalsIgnoreCase("codefragment")) {
            final String codefragment = xmlr.getElementText().trim();
            for (int i = 0; i < filesLine.size(); i++) {
              final String file = files.get(i);
              final Integer line = filesLine.get(i);
              final Violation violation =
                  violationBuilder() //
                      .setParser(CODENARC) //
                      .setFile(file) //
                      .setMessage(codefragment) //
                      .setRule("DUPLICATION") //
                      .setSeverity(getSeverity(tokens)) //
                      .setStartLine(line) //
                      .build();
              violations.add(violation);
            }
            files.clear();
            filesLine.clear();
          }
        }
      }
    }
    return violations;
  }
}
