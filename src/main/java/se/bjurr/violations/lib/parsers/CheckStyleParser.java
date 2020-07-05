package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CHECKSTYLE;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class CheckStyleParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    final List<String> files = getChunks(string, "<file", "</file>");
    for (final String fileChunk : files) {
      final String filename = getAttribute(fileChunk, "name");
      final List<String> errors = getChunks(fileChunk, "<error", "/>");
      final List<String> longFormErrors = getChunks(fileChunk, "<error", "</error>");
      errors.addAll(longFormErrors);
      for (final String errorChunk : errors) {
        final Integer line = getIntegerAttribute(errorChunk, "line");
        final Optional<Integer> column = findIntegerAttribute(errorChunk, "column");
        final String severity = getAttribute(errorChunk, "severity");
        final String message = getAttribute(errorChunk, "message");
        final String rule = findAttribute(errorChunk, "source").orElse(null);
        violations.add( //
            violationBuilder() //
                .setParser(CHECKSTYLE) //
                .setStartLine(line) //
                .setColumn(column.orElse(null)) //
                .setFile(filename) //
                .setSeverity(this.toSeverity(severity)) //
                .setMessage(message) //
                .setRule(rule) //
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
