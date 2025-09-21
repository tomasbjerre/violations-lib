package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.SEMGREP;

import com.google.gson.Gson;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class SemgrepParser implements ViolationsParser {

  @SuppressFBWarnings({
    "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD",
    "NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"
  })
  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new LinkedHashSet<>();
    final SemgrepReport report = new Gson().fromJson(reportContent, SemgrepReport.class);
    for (final SemgrepResult result : report.results) {
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
                .setMessage(
                    String.format(
                        "%s%n%n<p>%s</p>", result.extra.metadata.source, result.extra.message))
                .build());
      }
    }
    return violations;
  }

  @SuppressFBWarnings("NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
  private static class SemgrepReport {
    public List<SemgrepResult> results;
  }

  @SuppressFBWarnings("NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
  private static class SemgrepResult {
    public String check_id;
    public Location end;
    public Extra extra;
    public String path;
    public Location start;
  }

  @SuppressFBWarnings("NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
  private static class Extra {
    public boolean is_ignored;
    public String message;
    public Metadata metadata;
    public String severity;

    public SEVERITY getSeverity() {
      switch (this.severity) {
        case "WARNING":
          return SEVERITY.WARN;
        case "ERROR":
          return SEVERITY.ERROR;
        default:
          return SEVERITY.INFO;
      }
    }
  }

  @SuppressFBWarnings("NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
  private static class Location {
    public int col;
    public int line;
  }

  @SuppressFBWarnings("NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD")
  private static class Metadata {
    public String source;
  }
}
