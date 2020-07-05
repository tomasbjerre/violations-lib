package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.ANDROIDLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getChunks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class AndroidLintParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    final List<String> issues = getChunks(string, "<issue", "</issue>");
    for (final String issueChunk : issues) {
      /** Only ever going to be one location. */
      final String location = getChunks(issueChunk, "<location", "/>").get(0);

      final String filename = getAttribute(location, "file");
      final Optional<Integer> line = findIntegerAttribute(location, "line");
      final Optional<Integer> charAttrib = findIntegerAttribute(location, "column");
      final String severity = getAttribute(issueChunk, "severity");

      final String id = getAttribute(issueChunk, "id");
      final String message = getAttribute(issueChunk, "message");
      final String summary = getAttribute(issueChunk, "summary").trim();
      final String explanation = getAttribute(issueChunk, "explanation");

      final String category = getAttribute(issueChunk, "category");

      violations.add( //
          violationBuilder()
              .setParser(ANDROIDLINT)
              .setStartLine(line.orElse(0))
              .setColumn(charAttrib.orElse(null))
              .setFile(filename)
              .setSeverity(this.toSeverity(severity))
              .setRule(id)
              .setCategory(category)
              .setMessage(summary + "\n" + message + "\n" + explanation) //
              .build());
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
