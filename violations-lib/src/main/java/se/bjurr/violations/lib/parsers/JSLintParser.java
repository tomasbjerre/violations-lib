package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.JSLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class JSLintParser implements ViolationsParser {
  @Override
  public Set<Violation> parseReportOutput(
      final String content, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();

    try (InputStream input = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))) {
      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);
      String filename = null;
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("file")) {
            filename = getAttribute(xmlr, "name");
          }
          if (xmlr.getLocalName().equalsIgnoreCase("issue")) {
            final Integer line = getIntegerAttribute(xmlr, "line");
            final Integer charAttrib = getIntegerAttribute(xmlr, "char");
            final String severity = getAttribute(xmlr, "severity");
            final String reason = getAttribute(xmlr, "reason").trim();
            final String evidence = getAttribute(xmlr, "evidence").trim();
            final String message = reason + ": " + evidence;
            final Violation violation =
                violationBuilder()
                    .setParser(JSLINT) //
                    .setStartLine(line) //
                    .setColumn(charAttrib) //
                    .setFile(filename) //
                    .setSeverity(this.toSeverity(severity)) //
                    .setMessage(message) //
                    .build();
            violations.add(violation);
          }
        }
      }
    }
    return violations;
  }

  public SEVERITY toSeverity(final String severity) {
    if (severity.equalsIgnoreCase("E")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("W")) {
      return WARN;
    }
    return INFO;
  }
}
