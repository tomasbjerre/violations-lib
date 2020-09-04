package se.bjurr.violations.lib.parsers;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.WARNING;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.reports.Parser.JUNIT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;

public class JUnitParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();

    final List<String> errors = getChunks(reportContent, "<testcase", "(/>|</testcase>)");

    for (final String errorChunk : errors) {
      final List<String> failureChunks = getChunks(errorChunk, "<failure", "</failure>");
      final List<String> errorChunks = getChunks(errorChunk, "<error", "</error>");
      final ArrayList<String> chunks = new ArrayList<>();
      chunks.addAll(failureChunks);
      chunks.addAll(errorChunks);

      for (final String failure : chunks) {
        final Optional<String> messageOpt = findAttribute(failure, "message");
        String message = messageOpt.orElse(findAttribute(failure, "type").orElse(null));
        if (message == null) {
          message = failure.replaceAll("(<failure[^>]*>|</failure>)", "");
        }
        final String className = getAttribute(errorChunk, "classname");
        final String name = getAttribute(errorChunk, "name");
        final String type = findAttribute(errorChunk, "type").orElse(null);

        final List<String> failLine = getChunks(failure, className + "." + name, "\\)");

        String fileNameLine;
        if (failLine.isEmpty()) {
          violationsLogger.log(FINE, "Found failure, but failed to find fail line from stacktrace");
          fileNameLine = getContent(failure, "failure");
        } else {
          final List<String> fileNameAndLine = getChunks(failLine.get(0), "\\(", "\\)");

          if (fileNameAndLine.isEmpty()) {
            violationsLogger.log(
                WARNING,
                "Found failure line from stacktrace. But failed to get File name and line number from it.");
            continue;
          }
          fileNameLine = fileNameAndLine.get(0);
        }

        final String[] split = fileNameLine.split("[.\\:\\)]");

        if (split.length < 3) {
          violationsLogger.log(WARNING, "Failed to split Filename to its ending and line number");
          continue;
        }

        int lineOfFailure;

        try {
          lineOfFailure = Integer.parseInt(split[2]);
        } catch (final NumberFormatException e) {
          violationsLogger.log(WARNING, "Failed to parse line number from: " + split[2]);
          continue;
        }

        violations.add(
            Violation.violationBuilder()
                .setParser(JUNIT)
                .setMessage(name + " : " + message)
                .setStartLine(lineOfFailure)
                .setFile(className.replaceAll("\\.", "/") + "." + split[1])
                .setSource(className)
                .setRule(type)
                .setSeverity(ERROR) //
                .build());
      }
    }
    return violations;
  }
}
