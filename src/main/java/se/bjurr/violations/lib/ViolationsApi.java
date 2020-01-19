package se.bjurr.violations.lib;

import static java.util.logging.Level.FINE;
import static se.bjurr.violations.lib.reports.ReportsFinder.findAllReports;
import static se.bjurr.violations.lib.util.Utils.checkNotNull;
import static se.bjurr.violations.lib.util.Utils.setReporter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.model.codeclimate.CodeClimate;
import se.bjurr.violations.lib.model.codeclimate.CodeClimateTransformer;
import se.bjurr.violations.lib.reports.Parser;

public class ViolationsApi {
  private static Logger LOG = Logger.getLogger(ViolationsApi.class.getSimpleName());

  private String pattern;
  private Parser parser;
  private File startFile;
  private String reporter;

  public static ViolationsApi violationsApi() {
    return new ViolationsApi();
  }

  private ViolationsApi() {}

  public ViolationsApi findAll(final Parser parser) {
    this.parser = checkNotNull(parser, "parser");
    return this;
  }

  public ViolationsApi withReporter(final String reporter) {
    this.reporter = checkNotNull(reporter, "reporter");
    return this;
  }

  public ViolationsApi inFolder(final String folder) {
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
    final Set<Violation> foundViolations = parser.findViolations(includedFiles);
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
                + v.getRule()
                + ") "
                + v.getFile()
                + " "
                + v.getStartLine()
                + " -> "
                + v.getEndLine());
      }
    }
    return new ArrayList<Violation>(foundViolations);
  }

  public List<CodeClimate> codeClimate() {
    return CodeClimateTransformer.fromViolations(this.violations());
  }

  private String makeWindowsFriendly(final String regularExpression) {
    return regularExpression.replace("/", "(?:/|\\\\)");
  }

  public ViolationsApi withPattern(final String regularExpression) {
    pattern = makeWindowsFriendly(regularExpression);
    return this;
  }
}
