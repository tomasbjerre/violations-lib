package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CSSLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getChunks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class CSSLintParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    final List<String> files = getChunks(string, "<file", "</file>");
    for (final String fileChunk : files) {
      final String filename = getAttribute(fileChunk, "name");
      final List<String> issues = getChunks(fileChunk, "<issue", "/>");
      for (final String issueChunk : issues) {
        final Integer line = findIntegerAttribute(issueChunk, "line").orElse(1);
        final Optional<Integer> charAttrib = findIntegerAttribute(issueChunk, "char");
        final String severity = getAttribute(issueChunk, "severity");

        final String message = getAttribute(issueChunk, "reason");
        final String evidence = findAttribute(issueChunk, "evidence").orElse("").trim();
        violations.add( //
            violationBuilder() //
                .setParser(CSSLINT) //
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
