package se.bjurr.violations.lib.parsers;

import static java.util.logging.Level.FINE;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.SONAR;

import com.google.gson.Gson;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class SonarParser implements ViolationsParser {

  static class SonarReportIssue {
    String component;
    int line;
    SonarIssueTextRange textRange;
    Integer startLine;
    Integer startOffset;
    Integer endLine;
    Integer endOffset;
    String message;
    String severity;
    String rule;

    public String getFile() {
      try {
        final String[] parts = this.component.split(":");
        return parts[parts.length - 1];
      } catch (final Throwable t) {
        throw new RuntimeException("Cannot understand file " + this.component);
      }
    }

    public String getCategory() {
      try {
        return this.rule.split(":")[0];
      } catch (final Throwable t) {
        throw new RuntimeException("Cannot understand category " + this.rule);
      }
    }

    public SEVERITY getSeverity() {
      if (this.severity == null) {
        return null;
      }
      if (this.severity.equalsIgnoreCase("blocker")) {
        return SEVERITY.ERROR;
      }
      if (this.severity.equalsIgnoreCase("critical") || this.severity.equalsIgnoreCase("major")) {
        return SEVERITY.WARN;
      }
      return SEVERITY.INFO;
    }

    @Override
    public String toString() {
      return "SonarReportIssue [component="
          + this.component
          + ", line="
          + this.line
          + ", startLine="
          + this.startLine
          + ", endLine="
          + this.endLine
          + ", message="
          + this.message
          + ", severity="
          + this.severity
          + ", rule="
          + this.rule
          + "]";
    }
  }

  static class SonarIssueTextRange {
    int startLine;
    int startOffset;
    int endLine;
    int endOffset;
  }

  static class SonarReport {
    String version;
    private List<SonarReportIssue> issues;

    public void setIssues(final List<SonarReportIssue> issues) {
      this.issues = issues;
    }

    public List<SonarReportIssue> getIssues() {
      if (this.issues == null) {
        return new ArrayList<>();
      }
      return this.issues;
    }
  }

  @Override
  @SuppressFBWarnings("UWF_UNWRITTEN_FIELD")
  public List<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final SonarReport sonarReport = new Gson().fromJson(string, SonarReport.class);

    final List<Violation> violations = new ArrayList<>();

    for (final SonarReportIssue issue : sonarReport.getIssues()) {

      if (issue.textRange != null) {
        // Issue reports from the SonarQube API versions >= 7.5 use a
        // textRange field for the
        // startLine/endLine fields.
        issue.startLine = issue.textRange.startLine;
        issue.startOffset = issue.textRange.startOffset;
        issue.endLine = issue.textRange.endLine;
        issue.endOffset = issue.textRange.endOffset;
      }

      Integer startColumn = null;
      if (issue.startOffset != null) {
        startColumn = issue.startOffset + 1;
      }
      Integer endColumn = null;
      if (issue.endOffset != null) {
        endColumn = issue.endOffset + 1;
      }

      if (issue.startLine == null || issue.getSeverity() == null) {
        violationsLogger.log(FINE, "Ignoring issue: " + issue);
        continue;
      }

      if (issue.message == null) {
        issue.message = "N/A";
      }

      violations.add(
          violationBuilder() //
              .setFile(issue.getFile()) //
              .setCategory(issue.getCategory()) //
              .setEndLine(issue.endLine) //
              .setEndColumn(endColumn) //
              .setMessage(issue.message) //
              .setParser(SONAR) //
              .setReporter(SONAR.name()) //
              .setRule(issue.rule) //
              .setSeverity(issue.getSeverity()) //
              .setStartLine(issue.startLine) //
              .setColumn(startColumn) //
              .setSource(issue.component) //
              .build());
    }
    return violations;
  }
}
