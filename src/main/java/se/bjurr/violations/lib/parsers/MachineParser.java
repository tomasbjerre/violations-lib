package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.MACHINE;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

/** SEVERITY|TYPE|ERROR_CODE|FILE_PATH|LINE|COLUMN|LENGTH|ERROR_MESSAGE */
public class MachineParser implements ViolationsParser {

  private static final int ERROR_MESSAGE = 8;
  private static final int LENGTH = 7;
  private static final int COLUMN = 6;
  private static final int LINE = 5;
  private static final int FILE_PATH = 4;
  private static final int ERROR_CODE = 3;
  private static final int TYPE = 2;
  private static final int SEVERITY = 1;

  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    final List<List<String>> partsPerLine =
        getLines(
            reportContent,
            "^([^\\|]+?)\\|([^\\|]+?)\\|([^\\|]+?)\\|([^\\|]+?)\\|([^\\|]+?)\\|([^\\|]+?)\\|([^\\|]+?)\\|(.+?)$");
    for (final List<String> parts : partsPerLine) {
      final String severity = parts.get(SEVERITY);
      final String type = parts.get(TYPE);
      final String errorCode = parts.get(ERROR_CODE);
      final String filePath = parts.get(FILE_PATH);
      final String line = parts.get(LINE);
      final String column = parts.get(COLUMN);
      final String length = parts.get(LENGTH);
      final String errorMessage = parts.get(ERROR_MESSAGE);
      violations.add( //
          violationBuilder() //
              .setParser(MACHINE) //
              .setStartLine(Integer.parseInt(line)) //
              .setColumn(Integer.parseInt(column)) //
              .setEndColumn(Integer.parseInt(length)) //
              .setFile(filePath) //
              .setSeverity(this.toSeverity(severity)) //
              .setMessage(errorMessage) //
              .setRule(errorCode) //
              .setCategory(type) //
              .build() //
          );
    }
    return violations;
  }

  public SEVERITY toSeverity(final String severity) {
    if (severity.equalsIgnoreCase("error")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("warning")) {
      return WARN;
    }
    return INFO;
  }
}
