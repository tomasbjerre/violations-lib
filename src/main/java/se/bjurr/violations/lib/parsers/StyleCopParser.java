package se.bjurr.violations.lib.parsers;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.STYLECOP;
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

public class StyleCopParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    final List<Violation> violations = new ArrayList<>();

    try (InputStream input = new ByteArrayInputStream(string.getBytes("UTF-8"))) {

      final XMLInputFactory factory = XMLInputFactory.newInstance();
      final XMLStreamReader xmlr = factory.createXMLStreamReader(input);

      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("Violation")) {
            final String section = getAttribute(xmlr, "Section");
            final String source = getAttribute(xmlr, "Source");
            final String ruleNamespace = getAttribute(xmlr, "RuleNamespace");
            final String rule = getAttribute(xmlr, "Rule");
            final String ruleId = getAttribute(xmlr, "RuleId");
            final Integer lineNumber = getIntegerAttribute(xmlr, "LineNumber");
            final String message = xmlr.getElementText().replaceAll("\\s+", " ");
            final SEVERITY severity = INFO;
            final String filename = source.replaceAll("\\\\", "/");
            violations.add( //
                violationBuilder() //
                    .setParser(STYLECOP) //
                    .setMessage(message) //
                    .setFile(filename) //
                    .setStartLine(lineNumber) //
                    .setRule(rule) //
                    .setSeverity(severity) //
                    .setSource(filename) //
                    .setSpecific("section", section) //
                    .setSpecific("source", source) //
                    .setSpecific("ruleNamespace", ruleNamespace) //
                    .setSpecific("rule", rule) //
                    .setSpecific("ruleId", ruleId) //
                    .build() //
                );
          }
        }
      }
    }
    return violations;
  }
}
