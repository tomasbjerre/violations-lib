package se.bjurr.violations.lib.parsers;

import static java.nio.charset.StandardCharsets.UTF_8;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.RESHARPER;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class ResharperParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String content, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    try (InputStream input = new ByteArrayInputStream(content.getBytes(UTF_8))) {
      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);
      final Map<String, Map<String, String>> issueTypesPerTypeId = new HashMap<>();
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("IssueType")) {
            final Map<String, String> issueType = new HashMap<>();
            final String id = getAttribute(xmlr, "Id");
            issueType.put("category", getAttribute(xmlr, "Category"));
            final Optional<String> description = findAttribute(xmlr, "Description");
            issueType.put(
                "description",
                !description.isPresent() || description.get().isEmpty()
                    ? id
                    : description.orElse(""));
            issueType.put("severity", getAttribute(xmlr, "Severity"));
            issueType.put("url", findAttribute(xmlr, "WikiUrl").orElse(null));
            issueTypesPerTypeId.put(id, issueType);
          }
          if (xmlr.getLocalName().equalsIgnoreCase("Issue")) {
            final String typeId = getAttribute(xmlr, "TypeId");
            final String filename = getAttribute(xmlr, "File");
            final String url = issueTypesPerTypeId.get(typeId).get("url");
            final String message =
                findAttribute(xmlr, "Message").orElse("")
                    + ". "
                    + issueTypesPerTypeId.get(typeId).get("category")
                    + ". "
                    + issueTypesPerTypeId.get(typeId).get("description")
                    + (url != null ? ". For more info, visit " + url : "");
            final Integer line = findIntegerAttribute(xmlr, "Line").orElse(0);
            final String severity = issueTypesPerTypeId.get(typeId).get("severity");
            final Violation violation =
                violationBuilder() //
                    .setParser(RESHARPER) //
                    .setStartLine(line) //
                    .setFile(filename) //
                    .setSeverity(this.toSeverity(severity)) //
                    .setMessage(message) //
                    .setRule(typeId) //
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
