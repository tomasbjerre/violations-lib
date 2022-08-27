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
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.model.generated.sarif.Artifact;
import se.bjurr.violations.lib.model.generated.sarif.Invocation;
import se.bjurr.violations.lib.model.generated.sarif.Location;
import se.bjurr.violations.lib.model.generated.sarif.Message;
import se.bjurr.violations.lib.model.generated.sarif.MessageStrings;
import se.bjurr.violations.lib.model.generated.sarif.MultiformatMessageString;
import se.bjurr.violations.lib.model.generated.sarif.Notification;
import se.bjurr.violations.lib.model.generated.sarif.PhysicalLocation;
import se.bjurr.violations.lib.model.generated.sarif.PropertyBag;
import se.bjurr.violations.lib.model.generated.sarif.Region;
import se.bjurr.violations.lib.model.generated.sarif.ReportingConfiguration;
import se.bjurr.violations.lib.model.generated.sarif.ReportingDescriptor;
import se.bjurr.violations.lib.model.generated.sarif.ReportingDescriptorReference;
import se.bjurr.violations.lib.model.generated.sarif.Result;
import se.bjurr.violations.lib.model.generated.sarif.Result.Level;
import se.bjurr.violations.lib.model.generated.sarif.Run;
import se.bjurr.violations.lib.model.generated.sarif.SarifSchema;
import se.bjurr.violations.lib.model.generated.sarif.Suppression;
import se.bjurr.violations.lib.model.generated.sarif.ToolComponent;
import se.bjurr.violations.lib.model.generated.sarif.ToolComponentReference;
import se.bjurr.violations.lib.reports.Parser;
import se.bjurr.violations.lib.util.Utils;

public class SarifParser implements ViolationsParser {
  public static final String SARIF_RESULTS_CORRELATION_GUID = "correlationGuid";
  public static final String SARIF_RESULTS_SUPPRESSED = "suppressed";

  /** 3.52.3 */
  private enum DescriptorElementOf {
    RULES,
    NOTIFICATIONS
  }

  private static class ParsedPhysicalLocation {
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

  private static class MessageStringsDeserializer implements JsonDeserializer<MessageStrings> {

    @Override
    public MessageStrings deserialize(
        final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
        throws JsonParseException {
      try {
        final MessageStrings messageStrings = new MessageStrings();

        for (final Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
          for (final Entry<String, JsonElement> valueEntry :
              entry.getValue().getAsJsonObject().entrySet()) {
            final MultiformatMessageString mv = new MultiformatMessageString();
            mv.setText(valueEntry.getValue().getAsString());
            messageStrings.getAdditionalProperties().put(entry.getKey(), mv);
          }
        }

        return messageStrings;
      } catch (final RuntimeException e) {
        return new MessageStrings();
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
            .registerTypeAdapter(MessageStrings.class, new MessageStringsDeserializer())
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
      specifics.put(SARIF_RESULTS_SUPPRESSED, this.isSuppressed(result) ? "true" : "false");
      final ReportingDescriptor reportingDescriptor =
          this.findReportingDescriptor(run, result, DescriptorElementOf.RULES).orElse(null);
      final String category = this.getCategory(reportingDescriptor);
      final String reporter = this.getReporter(run, result.getRule());

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
        final String fullMessage = this.toMessage(message, helpTextOpt, reportingDescriptor);
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
    for (final Invocation invocation : run.getInvocations()) {
      for (final Notification notification : invocation.getToolConfigurationNotifications()) {
        final ReportingDescriptorReference ref = notification.getAssociatedRule();
        final Integer ruleIndex = this.getRuleIndex(ref);
        final String ruleId = null;
        final ReportingDescriptor reportingDescriptor =
            this.findReportingDescriptor(
                    run, DescriptorElementOf.NOTIFICATIONS, ref, ruleIndex, ruleId)
                .orElse(null);
        final String reporter = this.getReporter(run, ref);

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
          final String message =
              this.extractMessage(notification.getMessage(), reportingDescriptor);
          if (message.isEmpty()) {
            continue;
          }
          violations.add(
              violationBuilder()
                  .setParser(Parser.SARIF)
                  .setFile(Violation.NO_FILE)
                  .setStartLine(Violation.NO_LINE)
                  .setRule(reportingDescriptorName)
                  .setMessage(message)
                  .setSeverity(severity)
                  .setReporter(reporter)
                  .build());
        }
      }
    }
    return violations;
  }

  private boolean isSuppressed(final Result result) {
    final List<Suppression> supressions =
        result.getSuppressions().stream()
            .filter(
                (it) -> {
                  return it.getState() != Suppression.State.UNDER_REVIEW
                      && it.getState() != Suppression.State.REJECTED;
                })
            .collect(Collectors.toList());
    return !supressions.isEmpty();
  }

  private String getReporter(final Run run, final ReportingDescriptorReference ref) {
    final ToolComponent tool = this.findToolComponent(run, ref);
    if (tool != null && tool.getName() != null && !tool.getName().trim().isEmpty()) {
      return tool.getName();
    }
    return "Sarif";
  }

  private Integer getRuleIndex(final ReportingDescriptorReference ref) {
    Integer ruleIndex = null;
    if (ref != null) {
      ruleIndex = ref.getIndex();
    }
    if (ruleIndex == null || ruleIndex == -1) {
      return null;
    }
    return ruleIndex;
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
        return properties.getCategory();
      }
    }
    return null;
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
    if (reportingDescriptor != null && reportingDescriptor.getId() != null) {
      fullMessage.append(reportingDescriptor.getId());
    }
    if (reportingDescriptor != null
        && reportingDescriptor.getName() != null
        && !isNullOrEmpty(reportingDescriptor.getName())) {
      fullMessage.append(": ").append(reportingDescriptor.getName());
    }
    if (reportingDescriptor != null
        && reportingDescriptor.getShortDescription() != null
        && !isNullOrEmpty(reportingDescriptor.getShortDescription().getMarkdown())) {
      fullMessage.append("\n\n").append(reportingDescriptor.getShortDescription().getMarkdown());
    } else if (reportingDescriptor != null
        && reportingDescriptor.getShortDescription() != null
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
    if (!Utils.isNullOrEmpty(text)) {
      return text;
    }
    if (message.getId() != null) {
      if (reportingDescriptor != null && reportingDescriptor.getMessageStrings() != null) {
        final String messageText =
            reportingDescriptor
                .getMessageStrings()
                .getAdditionalProperties()
                .get(message.getId())
                .getText();
        final List<String> arguments = message.getArguments();
        return this.renderString(messageText, arguments);
      }
    }
    if (reportingDescriptor != null && reportingDescriptor.getShortDescription() != null) {
      return reportingDescriptor.getShortDescription().toString();
    }
    return "";
  }

  private String renderString(String text, final List<String> arguments) {
    for (int i = 0; i < arguments.size(); i++) {
      text = text.replace("{" + i + "}", arguments.get(i));
    }
    return text;
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

  private Optional<ReportingDescriptor> findReportingDescriptor(
      final Run run, final Result result, final DescriptorElementOf lookIn) {
    final ReportingDescriptorReference ref = result.getRule();
    final Integer ruleIndex = this.findRuleIndex(result, ref);
    final String ruleId = result.getRuleId();
    return this.findReportingDescriptor(run, lookIn, ref, ruleIndex, ruleId);
  }

  private Optional<ReportingDescriptor> findReportingDescriptor(
      final Run run,
      final DescriptorElementOf lookIn,
      final ReportingDescriptorReference ref,
      final Integer ruleIndex,
      final String ruleId) {
    final ToolComponent tool = this.findToolComponent(run, ref);
    if (tool == null) {
      return Optional.empty();
    }
    if (ruleIndex != null) {
      return Optional.of(this.getReportingDescriptorByIndex(tool, ruleIndex, lookIn));
    }

    if (ref != null && ref.getGuid() != null) {
      return this.findReportingDescriptorByGui(tool, ref.getGuid(), lookIn);
    }
    if (ruleId != null) {
      return this.findReportingDescriptorByRuleId(tool, ruleId, lookIn);
    }
    return Optional.empty();
  }

  private Integer findRuleIndex(final Result result, final ReportingDescriptorReference ref) {
    Integer ruleIndex = result.getRuleIndex();
    if (ruleIndex == -1) {
      ruleIndex = null;
    }
    if (ruleIndex == null && ref != null) {
      ruleIndex = ref.getIndex();
    }
    return ruleIndex;
  }

  private ToolComponent findToolComponent(final Run run, final ReportingDescriptorReference ref) {
    if (run.getTool() == null) {
      return null;
    }
    if (ref == null) {
      return run.getTool().getDriver();
    }
    final ToolComponentReference toolRef = ref.getToolComponent();
    if (toolRef.getGuid() != null) {
      return this.getToolComponentByGui(run, toolRef.getGuid());
    }
    if (toolRef.getIndex() != null) {
      return this.getToolComponentByIndex(run, toolRef.getIndex());
    }
    return run.getTool().getDriver();
  }

  private ReportingDescriptor getReportingDescriptorByIndex(
      final ToolComponent tool, final Integer index, final DescriptorElementOf lookIn) {
    if (lookIn == DescriptorElementOf.RULES) {
      return new ArrayList<>(tool.getRules()).get(index);
    }
    if (lookIn == DescriptorElementOf.NOTIFICATIONS) {
      return new ArrayList<>(tool.getNotifications()).get(index);
    }
    throw new IllegalStateException(lookIn + " cannot find ReportingDescriptor");
  }

  private Optional<ReportingDescriptor> findReportingDescriptorByGui(
      final ToolComponent tool, final String guid, final DescriptorElementOf lookIn) {
    if (lookIn == DescriptorElementOf.RULES) {
      return tool.getRules().stream()
          .filter((it) -> it.getGuid() != null && it.getGuid().equals(guid))
          .findFirst();
    }
    if (lookIn == DescriptorElementOf.NOTIFICATIONS) {
      return tool.getNotifications().stream()
          .filter((it) -> it.getGuid() != null && it.getGuid().equals(guid))
          .findFirst();
    }
    return Optional.empty();
  }

  private Optional<ReportingDescriptor> findReportingDescriptorByRuleId(
      final ToolComponent tool, final String ruleId, final DescriptorElementOf lookIn) {
    if (lookIn == DescriptorElementOf.RULES) {
      return tool.getRules().stream()
          .filter((it) -> it.getId() != null && it.getId().equals(ruleId))
          .findFirst();
    }
    if (lookIn == DescriptorElementOf.NOTIFICATIONS) {
      return tool.getNotifications().stream()
          .filter((it) -> it.getId() != null && it.getId().equals(ruleId))
          .findFirst();
    }
    throw new IllegalStateException(lookIn + " cannot find ReportingDescriptor");
  }

  private ToolComponent getToolComponentByIndex(final Run run, final Integer index) {
    return new ArrayList<>(run.getTool().getExtensions()).get(index);
  }

  private ToolComponent getToolComponentByGui(final Run run, final String guid) {
    return run.getTool().getExtensions().stream()
        .filter((it) -> it.getGuid() != null && it.getGuid().equals(guid))
        .findFirst()
        .get();
  }
}
