package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.MSBULDLOG;
import static se.bjurr.violations.lib.util.Utils.isNullOrEmpty;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class MSBuildLogParser implements ViolationsParser {

  private static final String ParsingRegex =
      "^\\s*(.*)(\\(([0-9]*),([0-9]*)\\)|\\s):\\s([^\\s]*)\\s([^:]*):([^\\[]*)((\\[(.*)\\])?)$";

  private static final String[] MsBuildTargetFiles = {"Microsoft.Common.CurrentVersion.targets"};

  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    final List<List<String>> partsPerLine = getLines(reportContent, ParsingRegex);
    for (final List<String> parts : partsPerLine) {
      final String projectFile = parts.get(10);
      final String sourceFile = parts.get(1);

      final boolean isMsBuildTarget = isMsBuildTarget(sourceFile);

      Integer lineNumber = 0;
      if (parts.get(3) != null && !parts.get(3).isEmpty() && !isMsBuildTarget) {
        lineNumber = parseInt(parts.get(3));
      }
      Integer columnNumber = 0;
      if (parts.get(4) != null && !parts.get(4).isEmpty() && !isMsBuildTarget) {
        columnNumber = parseInt(parts.get(4));
      }
      final String severity = parts.get(5);
      final String rule = parts.get(6);
      final String message = parts.get(7).trim();
      violations.add( //
          violationBuilder() //
              .setParser(MSBULDLOG) //
              .setStartLine(lineNumber) //
              .setColumn(columnNumber) //
              .setFile(isMsBuildTarget ? projectFile : sourceFile) //
              .setSeverity(this.toSeverity(severity)) //
              .setRule(rule) //
              .setMessage(message) //
              .build() //
          );
    }
    return violations;
  }

  public SEVERITY toSeverity(final String severity) {
    if (isNullOrEmpty(severity)) {
      return INFO;
    }
    final String lowerCase = severity.toLowerCase(Locale.ENGLISH);
    if (lowerCase.contains("low")) {
      return INFO;
    }
    if (lowerCase.contains("error") || lowerCase.contains("c") || lowerCase.contains("high")) {
      return ERROR;
    }
    if (lowerCase.contains("warning") || lowerCase.contains("w") || lowerCase.contains("medium")) {
      return WARN;
    }
    return INFO;
  }

  private static boolean isMsBuildTarget(final String sourceName) {
    for (final String target : MsBuildTargetFiles) {
      if (sourceName.endsWith(target)) {
        return true;
      }
    }
    return false;
  }
}
