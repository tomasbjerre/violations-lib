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
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class SimianParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    final List<Violation> violations = new ArrayList<>();

    try (InputStream input = new ByteArrayInputStream(string.getBytes("UTF-8"))) {

      final XMLInputFactory factory = XMLInputFactory.newInstance();
      final XMLStreamReader xmlr = factory.createXMLStreamReader(input);

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
                    .setSeverity(toSeverity(lineCount)) //
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

  private SEVERITY toSeverity(Integer lineCount) {
    if (lineCount < 10) {
      return INFO;
    }
    if (lineCount < 50) {
      return WARN;
    }
    return ERROR;
  }
}
