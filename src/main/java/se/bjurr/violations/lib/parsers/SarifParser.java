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
import se.bjurr.violations.lib.model.generated.sarif.Invocation;
import se.bjurr.violations.lib.model.generated.sarif.Location;
import se.bjurr.violations.lib.model.generated.sarif.Message;
import se.bjurr.violations.lib.model.generated.sarif.Notification;
import se.bjurr.violations.lib.model.generated.sarif.PhysicalLocation;
import se.bjurr.violations.lib.model.generated.sarif.PropertyBag;
import se.bjurr.violations.lib.model.generated.sarif.Region;
import se.bjurr.violations.lib.model.generated.sarif.ReportingConfiguration;
import se.bjurr.violations.lib.model.generated.sarif.ReportingDescriptor;
import se.bjurr.violations.lib.model.generated.sarif.Result;
import se.bjurr.violations.lib.model.generated.sarif.Result.Level;
import se.bjurr.violations.lib.model.generated.sarif.Run;
import se.bjurr.violations.lib.model.generated.sarif.SarifSchema;
import se.bjurr.violations.lib.reports.Parser;
import se.bjurr.violations.lib.util.Utils;

public class SarifParser implements ViolationsParser {
  public static final String SARIF_RESULTS_CORRELATION_GUID = "correlationGuid";

  public class ParsedPhysicalLocation {
    public String regionMessage;
    public String filename;
    public Integer startLine;

    public ParsedPhysicalLocation() {
      this.filename = Violation.NO_FILE;
      this.startLine = Violation.NO_LINE;
      this.regionMessage = null;
    }
  }

  private static class ResultDeserializer implements JsonDeserializer<Result.Level> {

    @Override
    public Result.Level deserialize(
        final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
        throws JsonParseException {
      try {
        final String asString = json.getAsString();
        return Result.Level.fromValue(asString);
      } catch (final Exception e) {
        return Level.NONE;
      }
    }
  }

  private static class NotificationDeserializer implements JsonDeserializer<Notification.Level> {

    @Override
    public Notification.Level deserialize(
        final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
        throws JsonParseException {
      try {
        final String asString = json.getAsString();
        return Notification.Level.fromValue(asString);
      } catch (final Exception e) {
        return Notification.Level.NONE;
      }
    }
  }

  private static class ReportingConfigurationDeserializer
      implements JsonDeserializer<ReportingConfiguration.Level> {

    @Override
    public ReportingConfiguration.Level deserialize(
        final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
        throws JsonParseException {
      try {
        final String asString = json.getAsString();
        return ReportingConfiguration.Level.fromValue(asString);
      } catch (final Exception e) {
        return ReportingConfiguration.Level.NONE;
      }
    }
  }

  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final SarifSchema report =
        new GsonBuilder()
            .registerTypeAdapter(Result.Level.class, new ResultDeserializer())
            .registerTypeAdapter(Notification.Level.class, new NotificationDeserializer())
            .registerTypeAdapter(
                ReportingConfiguration.Level.class, new ReportingConfigurationDeserializer())
            .create()
            .fromJson(reportContent, SarifSchema.class);

    final Set<Violation> violations = new TreeSet<>();

    if (report.getRuns() == null) {
      return violations;
    }

    for (final Run run : report.getRuns()) {
      violations.addAll(this.parseNotifications(run));
      violations.addAll(this.parseResults(run));
    }

    return violations;
  }

  private Set<Violation> parseResults(final Run run) {
    final Set<Violation> violations = new TreeSet<>();
    final Map<String, ReportingDescriptor> rulesById = this.getRulesById(run);
    final String reporter = this.getReporter(run);
    for (final Result result : run.getResults()) {
      final String ruleId = result.getRuleId();
      final Message message = result.getMessage();
      if (message == null) {
        continue;
      }
      final Level level = result.getLevel();
      // Multiple instances of the same rule id / message / location are not added to
      // the violations collection. Parse unique identifier fields if they exist
      final Map<String, String> specifics = new HashMap<>();
      final String correlationGuid = result.getCorrelationGuid();
      if (!isNullOrEmpty(correlationGuid)) {
        specifics.put(SARIF_RESULTS_CORRELATION_GUID, correlationGuid);
      }

      final ReportingDescriptor reportingDescriptor = rulesById.get(ruleId);
      final String category = this.getCategory(reportingDescriptor);

      final Optional<String> helpTextOpt = this.findHelpText(reportingDescriptor);
      final List<Location> locations = this.filterLocations(result.getLocations());
      if (this.notEmptyOrNull(locations)) {
        for (final Location location : locations) {
          final ParsedPhysicalLocation parsedPhysicalLocation =
              this.parsePhysicalLocation(location.getPhysicalLocation(), run.getArtifacts());
          final String fullMessage =
              this.toMessage(message, helpTextOpt, parsedPhysicalLocation, reportingDescriptor);
          violations.add(
              violationBuilder()
                  .setParser(Parser.SARIF)
                  .setFile(parsedPhysicalLocation.filename)
                  .setStartLine(parsedPhysicalLocation.startLine)
                  .setRule(ruleId)
                  .setMessage(fullMessage)
                  .setSeverity(this.toSeverity(level, reportingDescriptor))
                  .setReporter(reporter)
                  .setCategory(category)
                  .setSpecifics(specifics)
                  .build());
        }
      } else {
        final String fullMessage =
              this.toMessage(message, helpTextOpt, reportingDescriptor);
        violations.add(
            violationBuilder()
                .setParser(Parser.SARIF)
                .setFile(Violation.NO_FILE)
                .setStartLine(Violation.NO_LINE)
                .setRule(ruleId)
                .setMessage(fullMessage)
                .setSeverity(this.toSeverity(level, reportingDescriptor))
                .setReporter(reporter)
                .setCategory(category)
                .setSpecifics(specifics)
                .build());
      }
    }
    return violations;
  }

  private Set<Violation> parseNotifications(final Run run) {
    final Set<Violation> violations = new TreeSet<>();
    final List<ReportingDescriptor> notifications = this.getNotifications(run);
    final String reporter = this.getReporter(run);
    for (final Invocation invocation : run.getInvocations()) {
      for (final Notification notification : invocation.getToolConfigurationNotifications()) {
        final ReportingDescriptor reportingDescriptor =
            this.getReportingDescriptor(notifications, notification);
        final String reportingDescriptorName = this.getName(reportingDescriptor);
        final SEVERITY severity = this.toSeverity(notification.getLevel(), reportingDescriptor);
        final List<Location> locations = this.filter(notification.getLocations());
        if (this.notEmptyOrNull(locations)) {
          for (final Location location : locations) {
            final ParsedPhysicalLocation parsedPhysicalLocation =
                this.parsePhysicalLocation(location.getPhysicalLocation(), run.getArtifacts());
            final Optional<String> helpTextOpt = Optional.empty();
            final String fullMessage =
                this.toMessage(
                    notification.getMessage(),
                    helpTextOpt,
                    parsedPhysicalLocation,
                    reportingDescriptor);
            violations.add(
                violationBuilder()
                    .setParser(Parser.SARIF)
                    .setFile(parsedPhysicalLocation.filename)
                    .setStartLine(parsedPhysicalLocation.startLine)
                    .setRule(reportingDescriptorName)
                    .setMessage(fullMessage)
                    .setSeverity(severity)
                    .setReporter(reporter)
                    .build());
          }
        } else {
          violations.add(
              violationBuilder()
                  .setParser(Parser.SARIF)
                  .setFile(Violation.NO_FILE)
                  .setStartLine(Violation.NO_LINE)
                  .setRule(reportingDescriptorName)
                  .setMessage(this.extractMessage(notification.getMessage(), reportingDescriptor))
                  .setSeverity(severity)
                  .setReporter(reporter)
                  .build());
        }
      }
    }
    return violations;
  }

  private String getName(final ReportingDescriptor reportingDescriptor) {
    if (reportingDescriptor != null) {
      return reportingDescriptor.getName();
    }
    return null;
  }

  private String getCategory(final ReportingDescriptor reportingDescriptor) {
    if (reportingDescriptor != null) {
      final PropertyBag properties = reportingDescriptor.getProperties();
      if (properties != null && properties.getCategory() != null) {
        return properties.getCategory().toString();
      }
    }
    return null;
  }

  private String getReporter(final Run run) {
    if (run.getTool() != null
        && run.getTool().getDriver() != null
        && run.getTool().getDriver().getName() != null
        && !run.getTool().getDriver().getName().trim().isEmpty()) {
      return run.getTool().getDriver().getName();
    }
    return "Sarif";
  }

  private List<ReportingDescriptor> getNotifications(final Run run) {
    if (run.getTool() == null
        || run.getTool().getDriver() == null
        || run.getTool().getDriver().getNotifications() == null) {
      return new ArrayList<>();
    }
    return new ArrayList<>(run.getTool().getDriver().getNotifications());
  }

  private boolean notEmptyOrNull(final List<Location> locations) {
    return locations != null && locations.size() > 0;
  }

  private List<Location> filter(final Set<Location> locations) {
    return this.filterLocations(new ArrayList<>(locations));
  }

  private List<Location> filterLocations(final List<Location> locations) {
    if (locations == null) {
      return new ArrayList<>();
    }
    return locations.stream()
        .filter(
            (it) -> {
              return it.getPhysicalLocation() != null
                  && it.getPhysicalLocation().getRegion() != null
                  && it.getPhysicalLocation().getRegion().getStartLine() != null;
            })
        .collect(Collectors.toList());
  }

  private ReportingDescriptor getReportingDescriptor(
      final List<ReportingDescriptor> notifications, final Notification notification) {
    if (notification.getDescriptor() == null) {
      return null;
    }
    final Integer notificationIndex = notification.getDescriptor().getIndex();
    if (notificationIndex == null) {
      return null;
    }
    return notifications.get(notificationIndex);
  }

  private String toMessage(
      final Message message,
      final Optional<String> helpTextOpt,
      final ParsedPhysicalLocation parsedPhysicalLocation,
      final ReportingDescriptor reportingDescriptor) {
    final StringBuilder fullMessage =
        new StringBuilder(this.extractMessage(message, reportingDescriptor));
    if (!Utils.isNullOrEmpty(parsedPhysicalLocation.regionMessage)) {
      fullMessage.append("\n\n").append(parsedPhysicalLocation.regionMessage);
    }
    if (helpTextOpt.isPresent()) {
      fullMessage.append("\n\nFor additional help see: ").append(helpTextOpt.get());
    }
    return fullMessage.toString().trim();
  }

  private String toMessage(
      final Message message,
      final Optional<String> helpTextOpt,
      final ReportingDescriptor reportingDescriptor) {
    final StringBuilder fullMessage = new StringBuilder();
    if(reportingDescriptor != null && reportingDescriptor.getId() != null) {
      fullMessage.append(reportingDescriptor.getId());
    }
    if (reportingDescriptor != null && reportingDescriptor.getName() != null && !isNullOrEmpty(reportingDescriptor.getName())) {
      fullMessage.append(": ").append(reportingDescriptor.getName());
    }
    if (reportingDescriptor != null && reportingDescriptor.getShortDescription() != null
        && !isNullOrEmpty(reportingDescriptor.getShortDescription().getMarkdown())) {
      fullMessage.append("\n\n").append(reportingDescriptor.getShortDescription().getMarkdown());
    } else if (reportingDescriptor != null && reportingDescriptor.getShortDescription() != null
        && !isNullOrEmpty(reportingDescriptor.getShortDescription().getText())) {
      fullMessage.append("\n\n").append(reportingDescriptor.getShortDescription().getText());
    }
    if (helpTextOpt.isPresent()) {
      fullMessage.append("\n\nFor additional help see: ").append(helpTextOpt.get());
    }
    final String messageText = this.extractMessage(message, null);
    if (fullMessage.indexOf(messageText) < 0) {
      fullMessage.append("\n\n").append(messageText);
    }
    return fullMessage.toString();
  }

  private ParsedPhysicalLocation parsePhysicalLocation(
      final PhysicalLocation physicalLocation, final Set<Artifact> artifacts) {
    final ParsedPhysicalLocation parsed = new ParsedPhysicalLocation();
    final Region region = physicalLocation.getRegion();
    if (region == null) {
      return parsed;
    }
    parsed.startLine = Optional.ofNullable(region.getStartLine()).orElse(Violation.NO_LINE);
    parsed.regionMessage = this.extractMessage(region.getMessage(), null);
    final Integer artifactLocationIndex = physicalLocation.getArtifactLocation().getIndex();
    if (artifactLocationIndex != null && artifactLocationIndex != -1) {
      parsed.filename =
          new ArrayList<>(artifacts).get(artifactLocationIndex).getLocation().getUri();
    } else {
      parsed.filename = physicalLocation.getArtifactLocation().getUri();
    }
    return parsed;
  }

  private String extractMessage(
      final Message message, final ReportingDescriptor reportingDescriptor) {
    if (message == null) {
      return "";
    }
    String text = message.getMarkdown();
    if (Utils.isNullOrEmpty(text)) {
      text = message.getText();
    }
    if (text != null) {
      return text;
    }
    if (reportingDescriptor != null && reportingDescriptor.getShortDescription() != null) {
      return reportingDescriptor.getShortDescription().toString();
    }
    return "";
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

  private SEVERITY toSeverity(
      final Notification.Level level, final ReportingDescriptor reportingDescriptor) {
    if (level == null) {
      return this.toSeverity(reportingDescriptor).orElse(SEVERITY.INFO);
    }
    if (level == Notification.Level.ERROR) {
      return SEVERITY.ERROR;
    }
    if (level == Notification.Level.WARNING) {
      return SEVERITY.WARN;
    }
    return SEVERITY.INFO;
  }

  private SEVERITY toSeverity(
      final Result.Level level, final ReportingDescriptor reportingDescriptor) {
    if (level == null) {
      return this.toSeverity(reportingDescriptor).orElse(SEVERITY.INFO);
    }
    if (level == Result.Level.ERROR) {
      return SEVERITY.ERROR;
    }
    if (level == Result.Level.WARNING) {
      return SEVERITY.WARN;
    }
    return SEVERITY.INFO;
  }

  private Optional<SEVERITY> toSeverity(final ReportingDescriptor reportingDescriptor) {
    if (reportingDescriptor != null
        && reportingDescriptor.getDefaultConfiguration() != null
        && reportingDescriptor.getDefaultConfiguration().getLevel() != null) {
      final ReportingConfiguration.Level level =
          reportingDescriptor.getDefaultConfiguration().getLevel();
      if (level == ReportingConfiguration.Level.ERROR) {
        return Optional.of(SEVERITY.ERROR);
      } else if (level == ReportingConfiguration.Level.WARNING) {
        return Optional.of(SEVERITY.WARN);
      }
    }
    return Optional.empty();
  }
}
