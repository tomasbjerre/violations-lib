package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.PERLCRITIC;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getParts;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class PerlCriticParser implements ViolationsParser {
  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    final List<String> lines = getLines(string);
    for (final String line : lines) {
      final List<String> parts =
          getParts(
              line,
              "\\(Severity: (\\d*)\\)$",
              "^([^:]*):",
              "^(.+?) at line ",
              "^(\\d*), ",
              "column (\\d*)\\.  ",
              "(.*)");
      if (parts.isEmpty()) {
        continue;
      }
      final Integer severity = parseInt(parts.get(0));
      final String filename = parts.get(1);
      final String message = parts.get(2);
      final Integer lineNumber = parseInt(parts.get(3));
      final Integer columnNumber = parseInt(parts.get(4));
      final String rule = parts.get(5);

      violations.add( //
          violationBuilder() //
              .setParser(PERLCRITIC) //
              .setStartLine(lineNumber) //
              .setColumn(columnNumber) //
              .setFile(filename) //
              .setRule(rule) //
              .setSeverity(this.toSeverity(severity)) //
              .setMessage(message) //
              .build() //
          );
    }
    return violations;
  }

  public SEVERITY toSeverity(final Integer severity) {
    if (severity >= 4) {
      return ERROR;
    }
    if (severity >= 3) {
      return WARN;
    }
    return INFO;
  }
}
