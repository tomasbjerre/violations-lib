package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.ANDROIDLINT;
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

public class AndroidLintParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String content, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();

    try (InputStream input = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))) {
      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);
      String severity = null;
      String id = null;
      String message = null;
      String summary = null;
      String explanation = null;
      String category = null;
      String filename = null;
      Optional<Integer> line = null;
      Optional<Integer> charAttrib = null;
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("issue")) {
            severity = getAttribute(xmlr, "severity");
            id = getAttribute(xmlr, "id");
            message = getAttribute(xmlr, "message");
            summary = getAttribute(xmlr, "summary").trim();
            explanation = getAttribute(xmlr, "explanation");
            category = getAttribute(xmlr, "category");
          }
          if (xmlr.getLocalName().equalsIgnoreCase("location")) {
            filename = getAttribute(xmlr, "file");
            line = findIntegerAttribute(xmlr, "line");
            charAttrib = findIntegerAttribute(xmlr, "column");
          }
        }
        if (eventType == XMLStreamConstants.END_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("issue")) {
            final Violation violation =
                violationBuilder()
                    .setParser(ANDROIDLINT)
                    .setStartLine(line.orElse(0))
                    .setColumn(charAttrib.orElse(null))
                    .setFile(filename)
                    .setSeverity(this.toSeverity(severity))
                    .setRule(id)
                    .setCategory(category)
                    .setMessage(summary + "\n" + message + "\n" + explanation) //
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
