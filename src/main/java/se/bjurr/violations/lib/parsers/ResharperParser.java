package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.RESHARPER;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getChunks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class ResharperParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(final String string) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    final List<String> issueTypeChunks = getChunks(string, "<IssueType ", "/>");
    final Map<String, Map<String, String>> issueTypesPerTypeId = new HashMap<>();
    for (final String issueTypesChunk : issueTypeChunks) {
      final Map<String, String> issueType = new HashMap<>();
      final String id = getAttribute(issueTypesChunk, "Id");
      issueType.put("category", getAttribute(issueTypesChunk, "Category"));
      final Optional<String> description = findAttribute(issueTypesChunk, "Description");
      issueType.put("description", description.orElse(id));
      issueType.put("severity", getAttribute(issueTypesChunk, "Severity"));
      issueType.put("url", findAttribute(issueTypesChunk, "WikiUrl").orElse(null));
      issueTypesPerTypeId.put(id, issueType);
    }

    final List<String> issueChunks = getChunks(string, "<Issue ", "/>");
    for (final String issueChunk : issueChunks) {
      final String typeId = getAttribute(issueChunk, "TypeId");
      final String filename = getAttribute(issueChunk, "File");
      final String url = issueTypesPerTypeId.get(typeId).get("url");
      final String message =
          findAttribute(issueChunk, "Message").orElse("")
              + ". "
              + issueTypesPerTypeId.get(typeId).get("category")
              + ". "
              + issueTypesPerTypeId.get(typeId).get("description")
              + (url != null ? ". For more info, visit " + url : "");
      final Integer line = findIntegerAttribute(issueChunk, "Line").orElse(0);
      final String severity = issueTypesPerTypeId.get(typeId).get("severity");
      violations.add( //
          violationBuilder() //
              .setParser(RESHARPER) //
              .setStartLine(line) //
              .setFile(filename) //
              .setSeverity(toSeverity(severity)) //
              .setMessage(message) //
              .setRule(typeId) //
              .build() //
          );
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
