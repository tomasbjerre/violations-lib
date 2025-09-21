package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.FLAKE8;
import static se.bjurr.violations.lib.util.Utils.isNullOrEmpty;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

/**
 * PyLint. Format used by Flake8.<br>
 * <code>
 * msg-template(pylint)='{path1}:{line2}: [{severity4}{code5}] {msg6}'
 * msg-template(default)='{path1}:{line2}:{column7}: {severity8}{code9} {msg10}'
 * </code>
 */
public class Flake8Parser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    final List<List<String>> partsPerLine =
        getLines(
            string,
            "([^:]+):(\\d+):( \\[([A-Z]{1,8})([0-9]{0,6})\\] (.+)|(\\d+): ([A-Z]{1,8})([0-9]{0,6}) (.+))");
    for (final List<String> parts : partsPerLine) {
      final String filename = parts.get(1);
      Integer line;
      try {
        line = parseInt(parts.get(2));
      } catch (final Exception e) {
        continue;
      }
      Integer column = null;
      String severity;
      String rule;
      String message;
      if (!isNullOrEmpty(parts.get(7))) {
        column = parseInt(parts.get(7));
        severity = parts.get(8);
        rule = parts.get(9);
        message = parts.get(10);
      } else {
        severity = parts.get(4);
        rule = parts.get(5);
        message = parts.get(6);
      }
      if (isNullOrEmpty(message)) {
        continue;
      }
      violations.add( //
          violationBuilder()
              .setParser(FLAKE8)
              .setStartLine(line)
              .setColumn(column)
              .setFile(filename)
              .setRule(severity + rule)
              .setSeverity(this.toSeverity(severity.substring(0, 1))) //
              .setMessage(message) //
              .build() //
          );
    }
    return violations;
  }

  /**
   *
   *
   * <pre>
   * The different message types are:
   * (C) convention, for programming standard violation
   * (R) refactor, for bad code smell
   * (W) warning, for python specific problems
   * (E) error, for much probably bugs in the code
   * (F) fatal, if an error occured which prevented pylint from doing
   *     further processing.
   * </pre>
   */
  public SEVERITY toSeverity(final String severity) {
    if (severity.equalsIgnoreCase("E") || severity.equalsIgnoreCase("F")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("W")) {
      return WARN;
    }
    return INFO;
  }
}
