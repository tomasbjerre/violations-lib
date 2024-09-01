package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.util.Utils.isNullOrEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
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
import se.bjurr.violations.lib.model.generated.sarif.ReportingDescriptorReference;
import se.bjurr.violations.lib.model.generated.sarif.Result;
import se.bjurr.violations.lib.model.generated.sarif.Run;
import se.bjurr.violations.lib.model.generated.sarif.SarifSchema;
import se.bjurr.violations.lib.model.generated.sarif.Suppression;
import se.bjurr.violations.lib.model.generated.sarif.ToolComponent;
import se.bjurr.violations.lib.reports.Parser;

public class SarifParser implements ViolationsParser {
  private static Logger LOGGER = Logger.getLogger(SarifParser.class.getSimpleName());
  public static final String SARIF_RESULTS_CORRELATION_GUID = "correlationGuid";
  public static final String SARIF_RESULTS_SUPPRESSED = "suppressed";

  static class ParsedPhysicalLocation {
    public String regionMessage;
    public String filename;
    public Integer startLine;

    public ParsedPhysicalLocation() {
      this.filename = Violation.NO_FILE;
      this.startLine = Violation.NO_LINE;
      this.regionMessage = null;
    }
  }

  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final SarifSchema report = SarifParserDeserializer.fromJson(reportContent);

    final Set<Violation> violations = new TreeSet<>();

    if (report.getRuns() == null) {
      return violations;
    }

    for (final Run run : report.getRuns()) {
      final Map<String, String> originalUriBaseIdsMap =
          SarifParserOriginalUri.getOriginalUriBaseIdsMap(run.getOriginalUriBaseIds());
      violations.addAll(this.parseNotifications(run, originalUriBaseIdsMap));
      violations.addAll(this.parseResults(run, originalUriBaseIdsMap));
    }

    return violations;
  }

  private Set<Violation> parseResults(
      final Run run, final Map<String, String> originalUriBaseIdsMap) {
    final Set<Violation> violations = new TreeSet<>();
    for (final Result result : run.getResults()) {
      String ruleId = SarifParserReportingDescriptorFinder.findRuleId(result, result.getRule());
      final Message message = result.getMessage();
      if (message == null) {
        continue;
      }
      final Object level = result.getLevel();
      // Multiple instances of the same rule id / message / location are not added to
      // the violations collection. Parse unique identifier fields if they exist
      final Map<String, String> specifics = new HashMap<>();
      final String correlationGuid = result.getCorrelationGuid();
      if (!isNullOrEmpty(correlationGuid)) {
        specifics.put(SARIF_RESULTS_CORRELATION_GUID, correlationGuid);
      }
      specifics.put(SARIF_RESULTS_SUPPRESSED, this.isSuppressed(result) ? "true" : "false");
      final ReportingDescriptor reportingDescriptor =
          SarifParserReportingDescriptorFinder.findReportingDescriptor(
                  run, result, SarifParserReportingDescriptorFinder.DescriptorElementOf.RULES)
              .orElse(null);
      final String category = this.getCategory(reportingDescriptor);
      final String reporter = this.getReporter(run, result.getRule());
      if (ruleId == null && reportingDescriptor != null) {
        ruleId = reportingDescriptor.getId();
      }

      if (this.notEmptyOrNull(result.getLocations())) {
        for (final Location location : result.getLocations()) {
          final ParsedPhysicalLocation parsedPhysicalLocation =
              this.parsePhysicalLocation(
                  location.getPhysicalLocation(), run.getArtifacts(), originalUriBaseIdsMap);
          final String fullMessage =
              SarifParserMessaging.getMessageText(
                  message, parsedPhysicalLocation, reportingDescriptor);
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
            SarifParserMessaging.getMessageText(message, null, reportingDescriptor);
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

  private Set<Violation> parseNotifications(
      final Run run, final Map<String, String> originalUriBaseIdsMap) {
    final Set<Violation> violations = new TreeSet<>();
    for (final Invocation invocation : run.getInvocations()) {
      for (final Notification notification : invocation.getToolConfigurationNotifications()) {
        final ReportingDescriptorReference ref = notification.getAssociatedRule();
        final String ruleId = null;
        final ReportingDescriptor reportingDescriptor =
            SarifParserReportingDescriptorFinder.findReportingDescriptor(
                    run,
                    SarifParserReportingDescriptorFinder.DescriptorElementOf.NOTIFICATIONS,
                    ref,
                    ruleId)
                .orElse(null);
        final String reporter = this.getReporter(run, ref);

        final String reportingDescriptorName = this.getName(reportingDescriptor);
        final SEVERITY severity = this.toSeverity(notification.getLevel(), reportingDescriptor);
        if (this.notEmptyOrNull(notification.getLocations())) {
          for (final Location location : notification.getLocations()) {
            final ParsedPhysicalLocation parsedPhysicalLocation =
                this.parsePhysicalLocation(
                    location.getPhysicalLocation(), run.getArtifacts(), originalUriBaseIdsMap);
            final String fullMessage =
                SarifParserMessaging.getMessageText(
                    notification.getMessage(), parsedPhysicalLocation, reportingDescriptor);
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
              SarifParserMessaging.getMessageText(notification.getMessage(), reportingDescriptor);
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

  private ParsedPhysicalLocation parsePhysicalLocation(
      final PhysicalLocation physicalLocation,
      final Set<Artifact> artifacts,
      final Map<String, String> originalUriBaseIdsMap) {
    final ParsedPhysicalLocation parsed = new ParsedPhysicalLocation();
    final Region region = physicalLocation.getRegion();
    if (region != null) {
      parsed.startLine = Optional.ofNullable(region.getStartLine()).orElse(Violation.NO_LINE);
      parsed.regionMessage = SarifParserMessaging.findMessageText(region.getMessage()).orElse("");
    } else {
      parsed.startLine = Violation.NO_LINE;
      parsed.regionMessage = "";
    }
    parsed.filename = "";
    final String uriBaseId = physicalLocation.getArtifactLocation().getUriBaseId();
    if (uriBaseId != null) {
      final String originalUriBaseId = originalUriBaseIdsMap.get(uriBaseId);
      if (originalUriBaseId == null) {
        LOGGER.warning(
            "Did not find '"
                + uriBaseId
                + "' in originalUriBaseIds "
                + originalUriBaseIdsMap.keySet());
      }
      if (originalUriBaseId != null && !originalUriBaseId.isEmpty()) {
        parsed.filename += originalUriBaseId;
      }
    }
    final Integer artifactLocationIndex = physicalLocation.getArtifactLocation().getIndex();
    if (artifactLocationIndex != null && artifactLocationIndex != -1) {
      parsed.filename +=
          new ArrayList<>(artifacts).get(artifactLocationIndex).getLocation().getUri();
    } else {
      parsed.filename += physicalLocation.getArtifactLocation().getUri();
    }
    return parsed;
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
    final ToolComponent tool = SarifParserReportingDescriptorFinder.findToolComponent(run, ref);
    if (tool != null && tool.getName() != null && !tool.getName().trim().isEmpty()) {
      return tool.getName();
    }
    return "Sarif";
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

  private boolean notEmptyOrNull(final Collection<Location> locations) {
    return locations != null && !locations.isEmpty();
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

  private SEVERITY toSeverity(final Object level, final ReportingDescriptor reportingDescriptor) {
    if (level == null) {
      return this.toSeverity(reportingDescriptor).orElse(SEVERITY.INFO);
    }
    if (level.equals("error")) {
      return SEVERITY.ERROR;
    }
    if (level.equals("warning")) {
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
