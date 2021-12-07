package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.Violation.violationBuilder;

import com.google.gson.Gson;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.model.generated.sarif.Location;
import se.bjurr.violations.lib.model.generated.sarif.Message;
import se.bjurr.violations.lib.model.generated.sarif.PhysicalLocation;
import se.bjurr.violations.lib.model.generated.sarif.Region;
import se.bjurr.violations.lib.model.generated.sarif.Result;
import se.bjurr.violations.lib.model.generated.sarif.Result.Level;
import se.bjurr.violations.lib.model.generated.sarif.Run;
import se.bjurr.violations.lib.model.generated.sarif.SarifSchema;
import se.bjurr.violations.lib.reports.Parser;
import se.bjurr.violations.lib.util.Utils;

public class SarifParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final SarifSchema report = new Gson().fromJson(reportContent, SarifSchema.class);

    final Set<Violation> violations = new TreeSet<>();
    if (report.getRuns() == null) {
      return violations;
    }
    for (final Run run : report.getRuns()) {
      for (final Result result : run.getResults()) {
        final String ruleId = result.getRuleId();
        final String message = result.getMessage().getText();
        if (Utils.isNullOrEmpty(message)) {
          continue;
        }
        final Level level = result.getLevel();
        for (final Location location : result.getLocations()) {
          final PhysicalLocation physicalLocation = location.getPhysicalLocation();
          final Region region = physicalLocation.getRegion();
          if (region == null) {
            continue;
          }
          final Integer startLine = region.getStartLine();
          if (startLine == null) {
            continue;
          }
          final String filename = physicalLocation.getArtifactLocation().getUri();
          final Message regionMessage = region.getMessage();
          String regionMessageText = "";
          if (regionMessage != null) {
            regionMessageText = regionMessage.getText();
          }
          violations.add(
              violationBuilder()
                  .setParser(Parser.SARIFPARSER)
                  .setFile(filename)
                  .setStartLine(startLine)
                  .setRule(ruleId)
                  .setMessage((message + " " + regionMessageText).trim())
                  .setSeverity(this.toSeverity(level))
                  .build());
        }
      }
    }
    return violations;
  }

  private SEVERITY toSeverity(final Level level) {
    if (level == Level.ERROR) {
      return SEVERITY.ERROR;
    }
    if (level == Level.WARNING) {
      return SEVERITY.WARN;
    }
    return SEVERITY.INFO;
  }
}
