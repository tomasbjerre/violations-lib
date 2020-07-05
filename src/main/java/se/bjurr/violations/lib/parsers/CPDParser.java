package se.bjurr.violations.lib.parsers;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CPD;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class CPDParser implements ViolationsParser {

  private SEVERITY getSeverity(final Integer from) {
    if (from < 100) {
      return INFO;
    }
    if (from < 1000) {
      return WARN;
    }
    return ERROR;
  }

  @Override
  public List<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    try (InputStream input = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8))) {

      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);

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
                      .setParser(CPD) //
                      .setFile(file) //
                      .setMessage(codefragment) //
                      .setRule("DUPLICATION") //
                      .setSeverity(this.getSeverity(tokens)) //
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
