package se.bjurr.violations.lib.parsers;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.GENDARME;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class GendarmeParser implements ViolationsParser {

  private SEVERITY getSeverity(final String severityString) {
    if (severityString.equalsIgnoreCase("Low")) {
      return INFO;
    } else if (severityString.equalsIgnoreCase("Medium")) {
      return WARN;
    } else if (severityString.equalsIgnoreCase("High")) {
      return ERROR;
    } else if (severityString.equalsIgnoreCase("Critical")) {
      return ERROR;
    }
    return WARN;
  }

  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();

    try (InputStream input = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8))) {

      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);

      String name = null;
      String problem = null;
      String solution = null;
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("rule")) {
            name = getAttribute(xmlr, "Name");
          }
          if (xmlr.getLocalName().equalsIgnoreCase("problem")) {
            problem = xmlr.getElementText().trim();
          }
          if (xmlr.getLocalName().equalsIgnoreCase("solution")) {
            solution = xmlr.getElementText().trim();
          }
          if (xmlr.getLocalName().equalsIgnoreCase("defect")) {
            final String severityString = getAttribute(xmlr, "Severity");
            final String source = getAttribute(xmlr, "Source");
            final SEVERITY severity = this.getSeverity(severityString);
            final String message = problem + "\n\n" + solution;
            final Pattern pattern = Pattern.compile("^(.*)\\(.([0-9]*)\\)$");
            final Matcher matcher = pattern.matcher(source);
            if (matcher.matches()) {
              final String filepath = matcher.group(1);
              final Integer lineNumber = Integer.parseInt(matcher.group(2));
              final Violation violation =
                  violationBuilder() //
                      .setParser(GENDARME) //
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
