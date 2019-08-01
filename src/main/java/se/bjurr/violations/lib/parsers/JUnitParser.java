package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getChunks;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;

public class JUnitParser implements ViolationsParser {
  private static Logger LOG = Logger.getLogger(JUnitParser.class.getSimpleName());

  @Override
  public List<Violation> parseReportOutput(final String reportContent) throws Exception {
    final List<Violation> violations = new ArrayList<>();

    final List<String> errors = getChunks(reportContent, "<testcase", "(/>|</testcase>)");

    for (final String errorChunk : errors) {
      final List<String> failureChunks = getChunks(errorChunk, "<failure", "</failure>");
      final List<String> errorChunks = getChunks(errorChunk, "<error", "</error>");
      final ArrayList<String> chunks = new ArrayList<>();
      chunks.addAll(failureChunks);
      chunks.addAll(errorChunks);

      for (final String failure : chunks) {
        final String message = getAttribute(failure, "message");
        final String className = getAttribute(errorChunk, "classname");
        final String name = getAttribute(errorChunk, "name");
        final String type = getAttribute(errorChunk, "type");

        final List<String> failLine = getChunks(failure, className + "." + name, "\\)");

        if (failLine.isEmpty()) {
          LOG.log(Level.WARNING, "Found failure, but failed to find fail line from stacktrace");
          continue;
        }
        final List<String> fileNameAndLine = getChunks(failLine.get(0), "\\(", "\\)");

        if (fileNameAndLine.isEmpty()) {
          LOG.log(
              Level.WARNING,
              "Found failure line from stacktrace. But failed to get File name and line number from it.");
          continue;
        }

        final String[] split = fileNameAndLine.get(0).split("[.\\:\\)]");

        if (split.length < 3) {
          LOG.log(Level.WARNING, "Failed to split Filename to its ending and line number");
          continue;
        }

        int lineOfFailure;

        try {
          lineOfFailure = Integer.parseInt(split[2]);
        } catch (final NumberFormatException e) {
          LOG.log(Level.WARNING, "Failed to parse line number from: " + split[2]);
          continue;
        }

        violations.add(
            Violation.violationBuilder()
                .setParser(Parser.JUNIT)
                .setMessage(name + " : " + message)
                .setStartLine(lineOfFailure)
                .setFile(className.replaceAll("\\.", "/") + "." + split[1])
                .setSource(className)
                .setRule(type)
                .setSeverity(SEVERITY.ERROR)
                .build());
      }
    }
    return violations;
  }
}
