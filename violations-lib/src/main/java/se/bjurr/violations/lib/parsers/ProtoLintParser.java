package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.PROTOLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;

public class ProtoLintParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    final List<List<String>> partsPerLine =
        getLines(reportContent, "\\[([^:]+):(\\d+):(\\d+)\\] (.+)");
    for (final List<String> parts : partsPerLine) {
      final String filename = parts.get(1);
      final Integer line = parseInt(parts.get(2));
      final Integer column = parseInt(parts.get(3));
      final String message = parts.get(4);
      violations.add(
          violationBuilder()
              .setParser(PROTOLINT)
              .setStartLine(line)
              .setColumn(column)
              .setFile(filename)
              .setSeverity(ERROR)
              .setMessage(message)
              .build());
    }
    return violations;
  }
}
