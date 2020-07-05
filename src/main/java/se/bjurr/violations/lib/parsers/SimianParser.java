package se.bjurr.violations.lib.parsers;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.SIMIAN;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class SimianParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();

    try (InputStream input = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8))) {

      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);

      String sourceFile = null;
      Integer lineCount = null;
      Integer startLineNumber = null;
      Integer endLineNumber = null;
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("set")) {
            lineCount = getIntegerAttribute(xmlr, "lineCount");
          }
          if (xmlr.getLocalName().equalsIgnoreCase("block")) {
            sourceFile = getAttribute(xmlr, "sourceFile");
            startLineNumber = getIntegerAttribute(xmlr, "startLineNumber");
            endLineNumber = getIntegerAttribute(xmlr, "endLineNumber");

            final Violation violation =
                violationBuilder() //
                    .setParser(SIMIAN) //
                    .setFile(sourceFile) //
                    .setMessage("Duplication") //
                    .setRule("DUPLICATION") //
                    .setSeverity(this.toSeverity(lineCount)) //
                    .setStartLine(startLineNumber) //
                    .setEndLine(endLineNumber) //
                    .build();
            violations.add(violation);
          }
        }
      }
    }
    return violations;
  }

  private SEVERITY toSeverity(final Integer lineCount) {
    if (lineCount < 10) {
      return INFO;
    }
    if (lineCount < 50) {
      return WARN;
    }
    return ERROR;
  }
}
