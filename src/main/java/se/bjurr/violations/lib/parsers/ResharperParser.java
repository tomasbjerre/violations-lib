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
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.Optional;

public class ResharperParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    List<Violation> violations = new ArrayList<>();
    List<String> issueTypeChunks = getChunks(string, "<IssueType ", "/>");
    Map<String, Map<String, String>> issueTypesPerTypeId = new HashMap<>();
    for (String issueTypesChunk : issueTypeChunks) {
      Map<String, String> issueType = new HashMap<>();
      String id = getAttribute(issueTypesChunk, "Id");
      issueType.put("category", getAttribute(issueTypesChunk, "Category"));
      Optional<String> description = findAttribute(issueTypesChunk, "Description");
      issueType.put("description", description.or(id));
      issueType.put("severity", getAttribute(issueTypesChunk, "Severity"));
      issueType.put("url", findAttribute(issueTypesChunk, "WikiUrl").orNull());
      issueTypesPerTypeId.put(id, issueType);
    }

    List<String> issueChunks = getChunks(string, "<Issue ", "/>");
    for (String issueChunk : issueChunks) {
      String typeId = getAttribute(issueChunk, "TypeId");
      String filename = getAttribute(issueChunk, "File");
      String url = issueTypesPerTypeId.get(typeId).get("url");
      String message =
          getAttribute(issueChunk, "Message")
              + ". "
              + issueTypesPerTypeId.get(typeId).get("category")
              + ". "
              + issueTypesPerTypeId.get(typeId).get("description")
              + (url != null ? ". For more info, visit " + url : "");
      Integer line = findIntegerAttribute(issueChunk, "Line").or(0);
      String severity = issueTypesPerTypeId.get(typeId).get("severity");
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

  public SEVERITY toSeverity(String severity) {
    if (severity.equalsIgnoreCase("ERROR")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("WARNING")) {
      return WARN;
    }
    return INFO;
  }
}
