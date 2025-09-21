package se.bjurr.violations.lib.parsers;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.STYLECOP;
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

public class StyleCopParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();

    try (InputStream input = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8))) {

      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);

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
                violationBuilder()
                    .setParser(STYLECOP)
                    .setMessage(message)
                    .setFile(filename)
                    .setStartLine(lineNumber)
                    .setRule(rule)
                    .setSeverity(severity)
                    .setSource(filename)
                    .setSpecific("section", section)
                    .setSpecific("source", source)
                    .setSpecific("ruleNamespace", ruleNamespace)
                    .setSpecific("rule", rule)
                    .setSpecific("ruleId", ruleId) //
                    .build());
          }
        }
      }
    }
    return violations;
  }
}
