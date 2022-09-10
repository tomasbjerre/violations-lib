package se.bjurr.violations.lib;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.INFO;
import static se.bjurr.violations.lib.FilteringViolationsLogger.filterLevel;
import static se.bjurr.violations.lib.reports.ReportsFinder.findAllReports;
import static se.bjurr.violations.lib.util.Utils.checkNotNull;
import static se.bjurr.violations.lib.util.Utils.setReporter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.model.codeclimate.CodeClimate;
import se.bjurr.violations.lib.model.codeclimate.CodeClimateTransformer;
import se.bjurr.violations.lib.model.generated.sarif.SarifSchema;
import se.bjurr.violations.lib.model.sarif.SarifTransformer;
import se.bjurr.violations.lib.parsers.ViolationsParser;
import se.bjurr.violations.lib.reports.Parser;
import se.bjurr.violations.lib.reports.ViolationsFinder;

@SuppressFBWarnings("CRLF_INJECTION_LOGS")
public class ViolationsApi {
  private final Logger LOGGER = Logger.getLogger(ViolationsApi.class.getSimpleName());
  private String pattern;
  private ViolationsParser violationsParser;
  private File startFile;
  private String reporter;
  private List<String> ignorePaths = new ArrayList<>();

  private ViolationsLogger violationsLogger =
      filterLevel(
          new ViolationsLogger() {

            @Override
            public void log(final Level level, final String string, final Throwable t) {
              ViolationsApi.this.LOGGER.log(level, string, t);
            }

            @Override
            public void log(final Level level, final String string) {
              ViolationsApi.this.LOGGER.log(level, string);
            }
          });

  public static ViolationsApi violationsApi() {
    return new ViolationsApi();
  }

  private ViolationsApi() {}

  public ViolationsApi findAll(final Parser parser) {
    this.violationsParser = checkNotNull(parser, "parser").getViolationsParser();
    return this;
  }

  public ViolationsApi withViolationsParser(final ViolationsParser violationsParser) {
    this.violationsParser = checkNotNull(violationsParser, "violationsParser");
    return this;
  }

  public ViolationsApi withViolationsLogger(final ViolationsLogger violationsLogger) {
    this.violationsLogger = checkNotNull(violationsLogger, "violationsLogger");
    return this;
  }

  public ViolationsApi withReporter(final String reporter) {
    this.reporter = checkNotNull(reporter, "reporter");
    return this;
  }

  @SuppressFBWarnings("PATH_TRAVERSAL_IN")
  public ViolationsApi inFolder(final String folder) {
    this.startFile = new File(checkNotNull(folder, "folder"));
    if (!this.startFile.exists()) {
      throw new RuntimeException(folder + " not found");
    }
    return this;
  }

  public ViolationsApi withIgnorePaths(final List<String> ignorePaths) {
    this.ignorePaths = checkNotNull(ignorePaths, "ignorePaths");
    return this;
  }

  public Set<Violation> violations() {
    final List<File> includedFiles =
        findAllReports(this.violationsLogger, this.startFile, this.pattern, this.ignorePaths);
    this.violationsLogger.log(
        INFO,
        "Found "
            + includedFiles.size()
            + " reports in "
            + this.startFile
            + " with pattern "
            + this.pattern);
    for (final File f : includedFiles) {
      this.violationsLogger.log(INFO, "    - " + f.getAbsolutePath());
    }
    final Set<Violation> foundViolations =
        new ViolationsFinder(this.violationsParser)
            .findViolations(this.violationsLogger, includedFiles);
    final boolean reporterWasSupplied = this.reporter != null && !this.reporter.trim().isEmpty();
    if (reporterWasSupplied) {
      setReporter(foundViolations, this.reporter);
    }

    if (this.LOGGER.isLoggable(FINE)) {
      this.violationsLogger.log(FINE, "Found " + foundViolations.size() + " violations:");
      for (final Violation v : foundViolations) {
        this.violationsLogger.log(
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
    return foundViolations;
  }

  public List<CodeClimate> codeClimate() {
    return CodeClimateTransformer.fromViolations(this.violations());
  }

  public SarifSchema sarif() {
    return SarifTransformer.fromViolations(this.violations());
  }

  private String makeWindowsFriendly(final String regularExpression) {
    return regularExpression.replace("/", "(?:/|\\\\)");
  }

  public ViolationsApi withPattern(final String regularExpression) {
    this.pattern = this.makeWindowsFriendly(regularExpression);
    return this;
  }
}
