package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.util.Utils.isNullOrEmpty;

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
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.model.generated.sarif.Artifact;
import se.bjurr.violations.lib.model.generated.sarif.Location;
import se.bjurr.violations.lib.model.generated.sarif.Message;
import se.bjurr.violations.lib.model.generated.sarif.PhysicalLocation;
import se.bjurr.violations.lib.model.generated.sarif.PropertyBag;
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
      final Map<String, ReportingDescriptor> rulesById = this.getRulesById(run);
      String reporter = "Sarif";
      final boolean isToolSet = run.getTool() != null && run.getTool().getDriver() != null;
      if (isToolSet
          && run.getTool().getDriver().getName() != null
          && !run.getTool().getDriver().getName().trim().isEmpty()) {
        reporter = run.getTool().getDriver().getName();
      }
      final List<Artifact> artifacts = new ArrayList<>(run.getArtifacts());
      for (final Result result : run.getResults()) {
        final String ruleId = result.getRuleId();
        final String message = this.extractMessage(result.getMessage());
        if (isNullOrEmpty(message)) {
          continue;
        }
        final Level level = result.getLevel();

        String category = null;
        if (rulesById.get(ruleId) != null) {
          final PropertyBag properties = rulesById.get(ruleId).getProperties();
          if (properties != null && properties.getCategory() != null) {
            category = properties.getCategory().toString();
          }
        }
        final Optional<String> helpTextOpt = this.findHelpText(rulesById.get(ruleId));
        final List<Location> locations = result.getLocations();
        final boolean hasLocations = locations != null && locations.size() > 0;
        if (hasLocations) {
          for (final Location location : locations) {
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
            final String regionMessage = this.extractMessage(region.getMessage());
            final StringBuilder fullMessage = new StringBuilder(message);
            if (regionMessage != null) {
              fullMessage.append("\n\n").append(regionMessage);
            }
            if (helpTextOpt.isPresent()) {
              fullMessage.append("\n\nFor additional help see: ").append(helpTextOpt.get());
            }
            violations.add(
                violationBuilder()
                    .setParser(Parser.SARIF)
                    .setFile(filename)
                    .setStartLine(startLine)
                    .setRule(ruleId)
                    .setMessage(fullMessage.toString().trim())
                    .setSeverity(this.toSeverity(level))
                    .setReporter(reporter)
                    .setCategory(category)
                    .build());
          }
        } else {
          violations.add(
              violationBuilder()
                  .setParser(Parser.SARIF)
                  .setFile(Violation.NO_FILE)
                  .setStartLine(Violation.NO_LINE)
                  .setRule(ruleId)
                  .setMessage(helpTextOpt.orElse(ruleId))
                  .setSeverity(this.toSeverity(level))
                  .setReporter(reporter)
                  .setCategory(category)
                  .build());
        }
      }
    }
    return violations;
  }

  /**
   * Returns the message text - favoring the markdown format.
   *
   * @param message the message from the Sarif result.
   * @return the message text which could be `null`.
   */
  protected String extractMessage(final Message message) {
    if (message == null) {
      return null;
    }
    String text = message.getMarkdown();
    if (Utils.isNullOrEmpty(text)) {
      text = message.getText();
    }
    return text;
  }

  private Map<String, ReportingDescriptor> getRulesById(final Run run) {
    if (run.getTool() != null
        && run.getTool().getDriver() != null
        && run.getTool().getDriver().getRules() != null) {
      return run.getTool().getDriver().getRules().stream()
          .collect(Collectors.toMap(ReportingDescriptor::getId, Function.identity()));
    }
    return new HashMap<>();
  }

  private Optional<String> findHelpText(final ReportingDescriptor r) {
    if (r == null) {
      return Optional.empty();
    }
    if (r.getHelp() != null && !isNullOrEmpty(r.getHelp().getMarkdown())) {
      return Optional.ofNullable(r.getHelp().getMarkdown());
    } else if (r.getHelp() != null && !isNullOrEmpty(r.getHelp().getText())) {
      return Optional.ofNullable(r.getHelp().getText());
    } else if (r.getFullDescription() != null
        && !isNullOrEmpty(r.getFullDescription().getMarkdown())) {
      return Optional.ofNullable(r.getFullDescription().getMarkdown());
    } else if (r.getFullDescription() != null && !isNullOrEmpty(r.getFullDescription().getText())) {
      return Optional.ofNullable(r.getFullDescription().getText());
    }
    return Optional.ofNullable(r.getName());
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
