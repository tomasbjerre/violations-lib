package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.JSLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class JSLintParser implements ViolationsParser {
  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    final List<String> files = getChunks(string, "<file", "</file>");
    for (final String fileChunk : files) {
      final String filename = getAttribute(fileChunk, "name");
      final List<String> issues = getChunks(fileChunk, "<issue", "/>");
      for (final String issueChunk : issues) {
        final Integer line = getIntegerAttribute(issueChunk, "line");
        final Integer charAttrib = getIntegerAttribute(issueChunk, "char");
        final String severity = getAttribute(issueChunk, "severity");
        final String reason = getAttribute(issueChunk, "reason").trim();
        final String evidence = getAttribute(issueChunk, "evidence").trim();
        final String message = reason + ": " + evidence;

        violations.add( //
            violationBuilder() //
                .setParser(JSLINT) //
                .setStartLine(line) //
                .setColumn(charAttrib) //
                .setFile(filename) //
                .setSeverity(this.toSeverity(severity)) //
                .setMessage(message) //
                .build() //
            );
      }
    }
    return violations;
  }

  public SEVERITY toSeverity(final String severity) {
    if (severity.equalsIgnoreCase("E")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("W")) {
      return WARN;
    }
    return INFO;
  }
}
