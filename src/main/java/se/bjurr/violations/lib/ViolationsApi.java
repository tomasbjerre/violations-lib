package se.bjurr.violations.lib;

import static java.util.logging.Level.FINE;
import static se.bjurr.violations.lib.reports.ReportsFinder.findAllReports;
import static se.bjurr.violations.lib.util.Utils.checkNotNull;
import static se.bjurr.violations.lib.util.Utils.setReporter;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;

public class ViolationsApi {
  private static Logger LOG = Logger.getLogger(ViolationsApi.class.getSimpleName());

  private String pattern;
  private Parser parser;
  private File startFile;
  private String reporter;

  public static String getDetailedReport(List<Violation> violations) {
    return new DetailedReportCreator(violations) //
        .create();
  }

  public static ViolationsApi violationsApi() {
    return new ViolationsApi();
  }

  private ViolationsApi() {}

  public ViolationsApi findAll(Parser parser) {
    this.parser = checkNotNull(parser, "parser");
    return this;
  }

  public ViolationsApi withReporter(String reporter) {
    this.reporter = checkNotNull(reporter, "reporter");
    return this;
  }

  public ViolationsApi inFolder(String folder) {
    startFile = new File(checkNotNull(folder, "folder"));
    if (!startFile.exists()) {
      throw new RuntimeException(folder + " not found");
    }
    return this;
  }

  public List<Violation> violations() {
    final List<File> includedFiles = findAllReports(startFile, pattern);
    if (LOG.isLoggable(FINE)) {
      LOG.log(FINE, "Found " + includedFiles.size() + " reports:");
      for (final File f : includedFiles) {
        LOG.log(FINE, f.getAbsolutePath());
      }
    }
    final List<Violation> foundViolations = parser.findViolations(includedFiles);
    final boolean reporterWasSupplied =
        reporter != null && !reporter.trim().isEmpty() && !reporter.equals(parser.name());
    if (reporterWasSupplied) {
      setReporter(foundViolations, reporter);
    }

    if (LOG.isLoggable(FINE)) {
      LOG.log(FINE, "Found " + foundViolations.size() + " violations:");
      for (final Violation v : foundViolations) {
        LOG.log(
            FINE,
            v.getReporter()
                + " "
                + v.getSeverity()
                + " ("
                + v.getRule().or("?")
                + ") "
                + v.getFile()
                + " "
                + v.getStartLine()
                + " -> "
                + v.getEndLine());
      }
    }
    return foundViolations;
  }

  public ViolationsApi withPattern(String regularExpression) {
    pattern = regularExpression;
    return this;
  }
}
