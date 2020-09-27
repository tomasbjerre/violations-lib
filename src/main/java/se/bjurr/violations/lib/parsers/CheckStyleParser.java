package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CHECKSTYLE;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class CheckStyleParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String content, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();

    try (InputStream input = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))) {
      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);
      String filename = null;
      Integer line = null;
      Optional<Integer> column = null;
      String severity = null;
      String message = null;
      String rule = null;
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("file")) {
            filename = getAttribute(xmlr, "name");
          }
          if (xmlr.getLocalName().equalsIgnoreCase("error")) {
            line = findIntegerAttribute(xmlr, "line").orElse(0);
            column = findIntegerAttribute(xmlr, "column");
            severity = getAttribute(xmlr, "severity");
            message = getAttribute(xmlr, "message");
            rule = findAttribute(xmlr, "source").orElse(null);
          }
        }
        if (eventType == XMLStreamConstants.END_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("error")) {
            final Violation violation =
                violationBuilder() //
                    .setParser(CHECKSTYLE) //
                    .setStartLine(line) //
                    .setColumn(column.orElse(null)) //
                    .setFile(filename) //
                    .setSeverity(this.toSeverity(severity)) //
                    .setMessage(message) //
                    .setRule(rule) //
                    .build();
            violations.add(violation);
          }
        }
      }
    }
    return violations;
  }

  public SEVERITY toSeverity(final String severity) {
    if (severity.equalsIgnoreCase("ERROR")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("WARNING")) {
      return WARN;
    }
    return INFO;
  }
}
