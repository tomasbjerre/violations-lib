package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.PMD;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

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

public class PMDParser implements ViolationsParser {

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
          if (xmlr.getLocalName().equalsIgnoreCase("violation")) {
            final Integer beginLine = getIntegerAttribute(xmlr, "beginline");
            final Integer endLine = getIntegerAttribute(xmlr, "endline");
            final Optional<Integer> beginColumn = findIntegerAttribute(xmlr, "begincolumn");
            final Optional<Integer> endColumn = findIntegerAttribute(xmlr, "endcolumn");
            final String rule = getAttribute(xmlr, "rule").trim();
            final Optional<String> ruleSetOpt = findAttribute(xmlr, "ruleset");
            final Optional<String> externalInfoUrlOpt = findAttribute(xmlr, "externalInfoUrl");
            final Integer priority = getIntegerAttribute(xmlr, "priority");
            final SEVERITY severity = this.toSeverity(priority);
            final String violationContent = xmlr.getElementText();
            final String message =
                violationContent
                    + "\n\n"
                    + ruleSetOpt.orElse("")
                    + " "
                    + externalInfoUrlOpt.orElse("");
            final Violation violation =
                violationBuilder()
                    .setParser(PMD)
                    .setStartLine(beginLine)
                    .setEndLine(endLine)
                    .setColumn(beginColumn.orElse(null))
                    .setEndColumn(endColumn.orElse(null))
                    .setFile(filename)
                    .setSeverity(severity)
                    .setRule(rule)
                    .setCategory(ruleSetOpt.orElse(null))
                    .setMessage(message.trim()) //
                    .build();
            violations.add(violation);
          }
        }
      }
    }
    return violations;
  }

  public SEVERITY toSeverity(final Integer priority) {
    if (priority < 3) {
      return ERROR;
    }
    if (priority < 5) {
      return WARN;
    }
    return INFO;
  }
}
