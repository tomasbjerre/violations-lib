package se.bjurr.violations.lib;

import static java.util.logging.Level.FINE;
import static se.bjurr.violations.lib.reports.ReportsFinder.findAllReports;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Reporter;

public class ViolationsReporterApi {
  private static Logger LOG = Logger.getLogger(ViolationsReporterApi.class.getSimpleName());

  public static ViolationsReporterApi violationsReporterApi() {
    return new ViolationsReporterApi();
  }

  private String pattern;
  private Reporter reporter;

  private File startFile;

  private ViolationsReporterApi() {}

  public ViolationsReporterApi findAll(Reporter reporter) {
    this.reporter = reporter;
    return this;
  }

  public ViolationsReporterApi inFolder(String folder) {
    startFile = new File(folder);
    if (!startFile.exists()) {
      throw new RuntimeException(folder + " not found");
    }
    return this;
  }

  public List<Violation> violations() {
    List<File> includedFiles = findAllReports(startFile, pattern);
    if (LOG.isLoggable(FINE)) {
      LOG.log(FINE, "Found " + includedFiles.size() + " reports:");
      for (File f : includedFiles) {
        LOG.log(FINE, f.getAbsolutePath());
      }
    }
    List<Violation> foundViolations = reporter.findViolations(includedFiles);
    if (LOG.isLoggable(FINE)) {
      LOG.log(FINE, "Found " + foundViolations.size() + " violations:");
      for (Violation v : foundViolations) {
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

  public ViolationsReporterApi withPattern(String regularExpression) {
    pattern = regularExpression;
    return this;
  }
}
