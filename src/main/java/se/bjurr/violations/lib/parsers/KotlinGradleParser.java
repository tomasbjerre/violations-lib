package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.KOTLINGRADLE;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class KotlinGradleParser implements ViolationsParser {
  @Override
  public Set<Violation> parseReportOutput(final String string, ViolationsLogger violationsLogger)
      throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    final List<List<String>> partsPerLine =
        getLines(string, "(w|e):([^:]*)[^\\d]+?(\\d+?)[^\\d]+?(\\d+?)[^:]+?:(.*)");
    for (final List<String> parts : partsPerLine) {
      final String severity = parts.get(1).trim();
      final String filename = parts.get(2).trim();
      final Integer line = parseInt(parts.get(3));
      final Integer column = parseInt(parts.get(4));
      final String message = parts.get(5).trim();
      violations.add( //
          violationBuilder() //
              .setParser(KOTLINGRADLE) //
              .setStartLine(line) //
              .setColumn(column) //
              .setFile(filename) //
              .setSeverity(this.toSeverity(severity)) //
              .setMessage(message) //
              .build() //
          );
    }
    return violations;
  }

  public SEVERITY toSeverity(final String severity) {
    if (severity.equalsIgnoreCase("e")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("w")) {
      return WARN;
    }
    return INFO;
  }
}
