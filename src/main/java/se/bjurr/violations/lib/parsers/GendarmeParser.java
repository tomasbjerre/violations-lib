package se.bjurr.violations.lib.parsers;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.reports.Reporter.GENDARME;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class GendarmeParser implements ViolationsParser {

  private SEVERITY getSeverity(String severityString) {
    if (severityString.equals("Low")) {
      return INFO;
    } else if (severityString.equals("Medium")) {
      return WARN;
    } else if (severityString.equals("High")) {
      return ERROR;
    } else if (severityString.equals("Critical")) {
      return ERROR;
    }
    return WARN;
  }

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    List<Violation> violations = new ArrayList<>();

    try (InputStream input = new ByteArrayInputStream(string.getBytes())) {

      XMLInputFactory factory = XMLInputFactory.newInstance();
      XMLStreamReader xmlr = factory.createXMLStreamReader(input);

      String name = null;
      String problem = null;
      String solution = null;
      while (xmlr.hasNext()) {
        int eventType = xmlr.next();
        if (eventType == START_ELEMENT) {
          if (xmlr.getLocalName().equals("rule")) {
            name = getAttribute(xmlr, "Name");
          }
          if (xmlr.getLocalName().equals("problem")) {
            problem = xmlr.getElementText().trim();
          }
          if (xmlr.getLocalName().equals("solution")) {
            solution = xmlr.getElementText().trim();
          }
          if (xmlr.getLocalName().equals("defect")) {
            String severityString = getAttribute(xmlr, "Severity");
            String source = getAttribute(xmlr, "Source");
            SEVERITY severity = getSeverity(severityString);
            String message = problem + "\n\n" + solution;
            Pattern pattern = Pattern.compile("^(.*)\\(.([0-9]*)\\)$");
            Matcher matcher = pattern.matcher(source);
            if (matcher.matches()) {
              String filepath = matcher.group(1);
              Integer lineNumber = Integer.parseInt(matcher.group(2));
              Violation violation =
                  violationBuilder() //
                      .setReporter(GENDARME) //
                      .setFile(filepath) //
                      .setMessage(message) //
                      .setRule(name) //
                      .setSeverity(severity) //
                      .setStartLine(lineNumber) //
                      .build();
              violations.add(violation);
            }
          }
        }
      }
    }
    return violations;
  }
}
