package se.bjurr.violations.lib.reports;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.SEVERE;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.parsers.ViolationsParser;
import se.bjurr.violations.lib.util.Utils;

public class ViolationsFinder {
  private final ViolationsParser violationsParser;

  public ViolationsFinder(final ViolationsParser violationsParser) {
    this.violationsParser = violationsParser;
  }

  public Set<Violation> findViolations(
      final ViolationsLogger violationsLogger, final List<File> includedFiles) {
    final Set<Violation> violations = new TreeSet<>();
    for (final File file : includedFiles) {
      String content = null;
      try {
        content = Utils.toString(Files.newInputStream(file.toPath()));
        if (Logger.getLogger(Parser.class.getSimpleName()).isLoggable(FINE)) {
          violationsLogger.log(
              FINE, "Using " + this.violationsParser.getClass().getName() + " to parse " + content);
        }
        violations.addAll(this.violationsParser.parseReportOutput(content, violationsLogger));
      } catch (final Throwable e) {
        final String withContent = content == null ? "" : " content:\n\n" + content;
        violationsLogger.log(
            SEVERE,
            "Error when parsing "
                + file.getAbsolutePath()
                + " as "
                + this.violationsParser.getClass().getName()
                + withContent,
            e);
      }
    }
    return violations;
  }
}
