package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.Violation.violationBuilder;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.model.generated.sarif.Artifact;
import se.bjurr.violations.lib.model.generated.sarif.Location;
import se.bjurr.violations.lib.model.generated.sarif.Message;
import se.bjurr.violations.lib.model.generated.sarif.PhysicalLocation;
import se.bjurr.violations.lib.model.generated.sarif.Region;
import se.bjurr.violations.lib.model.generated.sarif.ReportingDescriptor;
import se.bjurr.violations.lib.model.generated.sarif.Result;
import se.bjurr.violations.lib.model.generated.sarif.Result.Level;
import se.bjurr.violations.lib.model.generated.sarif.Run;
import se.bjurr.violations.lib.model.generated.sarif.SarifSchema;
import se.bjurr.violations.lib.reports.Parser;
import se.bjurr.violations.lib.util.Utils;

public class SarifParser implements ViolationsParser {

  private static class ResultDeserializer implements JsonDeserializer<Level> {

    @Override
    public Level deserialize(
        final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
        throws JsonParseException {
      try {
        final String asString = json.getAsString();
        return Level.fromValue(asString);
      } catch (final Exception e) {
        return Level.NONE;
      }
    }
  }

  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final SarifSchema report =
        new GsonBuilder()
            .registerTypeAdapter(Level.class, new ResultDeserializer())
            .create()
            .fromJson(reportContent, SarifSchema.class);

    final Set<Violation> violations = new TreeSet<>();
    if (report.getRuns() == null) {
      return violations;
    }
    for (final Run run : report.getRuns()) {
      final List<Artifact> artifacts = new ArrayList<>(run.getArtifacts());
      Map<String, String> helpMap = extractHelpText(run);
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
          String filename = null;
          final Integer artifactLocationIndex = physicalLocation.getArtifactLocation().getIndex();
          if (artifactLocationIndex != null && artifactLocationIndex != -1) {
            filename = artifacts.get(artifactLocationIndex).getLocation().getUri();
          } else {
            filename = physicalLocation.getArtifactLocation().getUri();
          }
          final Message regionMessage = region.getMessage();
          StringBuilder fullMessage = new StringBuilder(message);
          if (regionMessage != null) {
            fullMessage.append("\n\n").append(regionMessage.getText());
          }
          if (helpMap.containsKey(ruleId)) {
            fullMessage.append("\n\nFor additional help see: ").append(helpMap.get(ruleId));
          }
          violations.add(
              violationBuilder()
                  .setParser(Parser.SARIF)
                  .setFile(filename)
                  .setStartLine(startLine)
                  .setRule(ruleId)
                  .setMessage(fullMessage.toString().trim())
                  .setSeverity(this.toSeverity(level))
                  .build());
        }
      }
    }
    return violations;
  }

  private Map<String, String> extractHelpText(Run run) {
    Map<String, String> helpMap = new HashMap<>();
    if (run.getTool() != null
        && run.getTool().getDriver() != null
        && run.getTool().getDriver().getRules() != null) {
      for (ReportingDescriptor r : run.getTool().getDriver().getRules()) {
        if (r.getHelp() != null) {
          if (r.getHelp().getMarkdown() != null && !r.getHelp().getMarkdown().trim().isEmpty()) {
            helpMap.put(r.getId(), r.getHelp().getMarkdown());
          } else if (r.getHelp().getMarkdown() != null && !r.getHelp().getText().trim().isEmpty()) {
            helpMap.put(r.getId(), r.getHelp().getText());
          }
        }
      }
    }
    return helpMap;
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
