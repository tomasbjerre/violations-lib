package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;

import com.google.gson.Gson;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.model.generated.coverity.CoveritySchema;
import se.bjurr.violations.lib.model.generated.coverity.Issue;
import se.bjurr.violations.lib.reports.Parser;

public class CoverityParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final CoveritySchema coverityReport = new Gson().fromJson(string, CoveritySchema.class);

    final Set<Violation> violations = new TreeSet<>();
    final List<Issue> issues = coverityReport.getIssues();
    if (issues == null) {
      return violations;
    }
    for (final Issue issue : issues) {

      violations.add(
          violationBuilder() //
              .setFile(issue.getMainEventFilePathname()) //
              .setMessage(
                  issue.getCheckerProperties().getSubcategoryLocalEffect()
                      + "\n"
                      + issue.getCheckerProperties().getSubcategoryLocalEffect()) //
              .setParser(Parser.COVERITY) //
              .setCategory(issue.getCheckerProperties().getCategory())
              .setRule(issue.getType() + "/" + issue.getSubtype()) //
              .setSeverity(this.toSeverity(issue.getCheckerProperties().getImpact())) //
              .setStartLine(issue.getMainEventLineNumber()) //
              .build());
    }
    return violations;
  }

  private SEVERITY toSeverity(final String from) {
    if (from.equalsIgnoreCase("Medium")) {
      return WARN;
    }
    return ERROR;
  }
}
