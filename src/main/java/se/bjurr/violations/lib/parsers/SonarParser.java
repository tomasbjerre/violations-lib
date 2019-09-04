package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.SONAR;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class SonarParser implements ViolationsParser {
  private static final Logger LOG = Logger.getLogger(SonarParser.class.getSimpleName());

  static class SonarReportIssue {
    String component;
    int line;
    Integer startLine;
    Integer endLine;
    String message;
    String severity;
    String rule;

    public String getFile() {
      try {
        final String[] parts = component.split(":");
        return parts[parts.length - 1];
      } catch (final Throwable t) {
        throw new RuntimeException("Cannot understand file " + component);
      }
    }

    public String getCategory() {
      try {
        return rule.split(":")[0];
      } catch (final Throwable t) {
        throw new RuntimeException("Cannot understand category " + rule);
      }
    }

    public SEVERITY getSeverity() {
      if (severity == null) {
        return null;
      }
      if (severity.equalsIgnoreCase("blocker")) {
        return SEVERITY.ERROR;
      }
      if (severity.equalsIgnoreCase("critical") || severity.equalsIgnoreCase("major")) {
        return SEVERITY.WARN;
      }
      return SEVERITY.INFO;
    }

    @Override
    public String toString() {
      return "SonarReportIssue [component="
          + component
          + ", line="
          + line
          + ", startLine="
          + startLine
          + ", endLine="
          + endLine
          + ", message="
          + message
          + ", severity="
          + severity
          + ", rule="
          + rule
          + "]";
    }
  }

  static class SonarReport {
    String version;
    private List<SonarReportIssue> issues;

    public void setIssues(final List<SonarReportIssue> issues) {
      this.issues = issues;
    }

    public List<SonarReportIssue> getIssues() {
      if (issues == null) {
        return new ArrayList<>();
      }
      return issues;
    }
  }

  @Override
  public List<Violation> parseReportOutput(final String string) throws Exception {
    final SonarReport sonarReport = new Gson().fromJson(string, SonarReport.class);

    final List<Violation> violations = new ArrayList<>();
    for (final SonarReportIssue issue : sonarReport.getIssues()) {
      if (issue.startLine == null || issue.getSeverity() == null) {
        LOG.log(Level.FINE, "Ignoring issue: " + issue);
        continue;
      }
      violations.add(
          violationBuilder() //
              .setFile(issue.getFile()) //
              .setCategory(issue.getCategory()) //
              .setEndLine(issue.endLine) //
              .setMessage(issue.message) //
              .setParser(SONAR) //
              .setReporter(SONAR.name()) //
              .setRule(issue.rule) //
              .setSeverity(issue.getSeverity()) //
              .setStartLine(issue.startLine) //
              .setSource(issue.component) //
              .build());
    }
    return violations;
  }
}
