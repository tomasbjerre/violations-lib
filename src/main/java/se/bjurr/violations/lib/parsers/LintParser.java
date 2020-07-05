package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.LINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class LintParser implements ViolationsParser {
  @Override
  public List<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    final List<String> files = getChunks(string, "<file", "</file>");
    for (final String fileChunk : files) {
      final String filename = getAttribute(fileChunk, "name");
      final List<String> issues = getChunks(fileChunk, "<issue", "/>");
      for (final String issueChunk : issues) {
        final Integer line = getIntegerAttribute(issueChunk, "line");
        final Optional<Integer> charAttrib = findIntegerAttribute(issueChunk, "char");
        final String severity = getAttribute(issueChunk, "severity");

        final String message = getAttribute(issueChunk, "reason");
        final String evidence = getAttribute(issueChunk, "evidence").trim();
        violations.add( //
            violationBuilder() //
                .setParser(LINT) //
                .setStartLine(line) //
                .setColumn(charAttrib.orElse(null)) //
                .setFile(filename) //
                .setSeverity(this.toSeverity(severity)) //
                .setMessage(message + ": " + evidence) //
                .build() //
            );
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
