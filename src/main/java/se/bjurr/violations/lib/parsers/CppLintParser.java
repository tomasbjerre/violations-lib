package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CPPLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getParts;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class CppLintParser implements ViolationsParser {
  private static final Logger LOG = Logger.getLogger(CppLintParser.class.getSimpleName());

  @Override
  public List<Violation> parseReportOutput(final String string) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    final List<String> lines = getLines(string);
    for (final String line : lines) {
      final List<String> parts =
          getParts(line, "\\[([^\\]]*)\\]$", "\\[([^\\]]*)\\]$", "^([^:]*):", "^([^:]*):", "(.*)");
      if (parts.isEmpty()) {
        continue; // Happens for the line "Done processing cpp/test.cpp"
      }
      try {
        final Integer severity = parseInt(parts.get(0));
        final String rule = parts.get(1);
        final String filename = parts.get(2);
        int lineNumber;
        if (parts.get(3).equalsIgnoreCase("None")) {
          lineNumber = 0;
        } else {
          lineNumber = parseInt(parts.get(3));
        }
        final String message = parts.get(4);
        violations.add( //
            violationBuilder() //
                .setParser(CPPLINT) //
                .setStartLine(lineNumber) //
                .setFile(filename) //
                .setRule(rule) //
                .setSeverity(toSeverity(severity)) //
                .setMessage(message) //
                .build() //
            );
      } catch (final Exception e) {
        LOG.info("Was unable to parse: \"" + line + "\" found parts: " + parts);
      }
    }
    return violations;
  }

  public SEVERITY toSeverity(final Integer severity) {
    if (severity >= 5) {
      return ERROR;
    }
    if (severity >= 3) {
      return WARN;
    }
    return INFO;
  }
}
