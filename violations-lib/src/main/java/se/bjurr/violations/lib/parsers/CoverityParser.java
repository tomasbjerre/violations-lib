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
import se.bjurr.violations.lib.model.generated.coverity.CheckerProperty;
import se.bjurr.violations.lib.model.generated.coverity.CoveritySchema;
import se.bjurr.violations.lib.model.generated.coverity.Issue;
import se.bjurr.violations.lib.reports.Parser;
import se.bjurr.violations.lib.util.Utils;

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
              .setMessage(this.getMessage(issue.getCheckerProperties())) //
              .setParser(Parser.COVERITY) //
              .setCategory(issue.getCheckerProperties().getCategory())
              .setRule(issue.getType() + "/" + issue.getSubtype()) //
              .setSeverity(this.toSeverity(issue.getCheckerProperties().getImpact())) //
              .setStartLine(issue.getMainEventLineNumber()) //
              .build());
    }
    return violations;
  }

  private String getMessage(final CheckerProperty checkerProperty) {
    final boolean hasLongDescription =
        !Utils.isNullOrEmpty(checkerProperty.getSubcategoryLongDescription());
    final boolean hasLocalEffect =
        !Utils.isNullOrEmpty(checkerProperty.getSubcategoryLocalEffect());
    if (hasLongDescription && hasLocalEffect) {
      if (checkerProperty
          .getSubcategoryLongDescription()
          .contains(checkerProperty.getSubcategoryLocalEffect())) {
        return checkerProperty.getSubcategoryLongDescription();
      }
      return checkerProperty.getSubcategoryLongDescription()
          + ".\n"
          + checkerProperty.getSubcategoryLocalEffect();
    } else if (hasLongDescription) {
      return checkerProperty.getSubcategoryLongDescription();
    } else if (hasLocalEffect) {
      return checkerProperty.getSubcategoryLocalEffect();
    }
    return checkerProperty.getImpactDescription();
  }

  private SEVERITY toSeverity(final String from) {
    if (from.equalsIgnoreCase("Medium")) {
      return WARN;
    }
    return ERROR;
  }
}
