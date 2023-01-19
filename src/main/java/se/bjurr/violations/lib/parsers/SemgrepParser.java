package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.SEMGREP;

import com.google.gson.Gson;
import java.util.*;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class SemgrepParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(String reportContent, ViolationsLogger violationsLogger)
      throws Exception {
    final Set<Violation> violations = new LinkedHashSet<>();
    final SemgrepReport report = new Gson().fromJson(reportContent, SemgrepReport.class);
    for (SemgrepResult result : report.results) {
      if (!result.extra.is_ignored) {
        violations.add(
            violationBuilder()
                .setParser(SEMGREP)
                .setStartLine(result.start.line)
                .setColumn(result.start.col)
                .setEndLine(result.end.line)
                .setEndColumn(result.end.col)
                .setFile(result.path)
                .setSeverity(result.extra.getSeverity())
                .setRule(result.check_id)
                .setMessage(result.extra.message)
                .build());
      }
    }
    return violations;
  }

  private static class SemgrepReport {
    private String version;
    private List<SemgrepResult> results;
  }

  private static class SemgrepResult {
    private String check_id;
    private Location end;
    private Extra extra;
    private String path;
    private Location start;
  }

  private static class Extra {
    private String fingerprint;
    private boolean is_ignored;
    private String lines;
    private String message;
    private String severity;

    public SEVERITY getSeverity() {
      switch (severity) {
        case "INFO":
          return SEVERITY.INFO;
        case "WARNING":
          return SEVERITY.WARN;
        case "ERROR":
          return SEVERITY.ERROR;
        default:
          return null;
      }
    }
  }

  private static class Location {
    private int col;
    private int line;
    private int offset;
  }
}
