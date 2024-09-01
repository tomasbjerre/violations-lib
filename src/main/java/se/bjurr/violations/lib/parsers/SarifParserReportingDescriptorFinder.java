package se.bjurr.violations.lib.parsers;

import java.util.ArrayList;
import java.util.Optional;
import se.bjurr.violations.lib.model.generated.sarif.ReportingDescriptor;
import se.bjurr.violations.lib.model.generated.sarif.ReportingDescriptorReference;
import se.bjurr.violations.lib.model.generated.sarif.Result;
import se.bjurr.violations.lib.model.generated.sarif.Run;
import se.bjurr.violations.lib.model.generated.sarif.ToolComponent;
import se.bjurr.violations.lib.model.generated.sarif.ToolComponentReference;

public class SarifParserReportingDescriptorFinder {

  /** 3.52.3 */
  enum DescriptorElementOf {
    RULES,
    NOTIFICATIONS
  }

  static String findRuleId(final Result result, final ReportingDescriptorReference ref) {
    final String ruleId = result.getRuleId();
    if (ruleId == null && ref != null) {
      return ref.getId();
    }
    return ruleId;
  }

  static Optional<ReportingDescriptor> findReportingDescriptor(
      final Run run, final Result result, final DescriptorElementOf lookIn) {
    final ReportingDescriptorReference ref = result.getRule();
    final Integer ruleIndex = findRuleIndex(result, ref);
    final String ruleId = SarifParserReportingDescriptorFinder.findRuleId(result, ref);
    return SarifParserReportingDescriptorFinder.findReportingDescriptor(
        run, lookIn, ref, ruleIndex, ruleId);
  }

  static Optional<ReportingDescriptor> findReportingDescriptor(
      final Run run,
      final DescriptorElementOf lookIn,
      final ReportingDescriptorReference ref,
      final String ruleId) {
    final Integer ruleIndex = getRuleIndex(ref);
    return findReportingDescriptor(run, lookIn, ref, ruleIndex, ruleId);
  }

  static ToolComponent findToolComponent(final Run run, final ReportingDescriptorReference ref) {
    if (run.getTool() == null) {
      return null;
    }
    if (ref == null) {
      return run.getTool().getDriver();
    }
    final ToolComponentReference toolRef = ref.getToolComponent();
    if (toolRef == null) {
      return run.getTool().getDriver();
    }
    if (toolRef.getGuid() != null) {
      return getToolComponentByGui(run, toolRef.getGuid());
    }
    if (toolRef.getIndex() != null) {
      return getToolComponentByIndex(run, toolRef.getIndex());
    }
    return run.getTool().getDriver();
  }

  private static Optional<ReportingDescriptor> findReportingDescriptor(
      final Run run,
      final DescriptorElementOf lookIn,
      final ReportingDescriptorReference ref,
      final Integer ruleIndex,
      final String ruleId) {
    final ToolComponent tool = findToolComponent(run, ref);
    if (tool == null) {
      return Optional.empty();
    }
    if (ruleIndex != null) {
      return Optional.of(getReportingDescriptorByIndex(tool, ruleIndex, lookIn));
    }

    if (ref != null && ref.getGuid() != null) {
      return findReportingDescriptorByGui(tool, ref.getGuid(), lookIn);
    }
    if (ruleId != null) {
      return findReportingDescriptorByRuleId(tool, ruleId, lookIn);
    }
    return Optional.empty();
  }

  private static Integer getRuleIndex(final ReportingDescriptorReference ref) {
    Integer ruleIndex = null;
    if (ref != null) {
      ruleIndex = ref.getIndex();
    }
    if (ruleIndex == null || ruleIndex == -1) {
      return null;
    }
    return ruleIndex;
  }

  private static Integer findRuleIndex(
      final Result result, final ReportingDescriptorReference ref) {
    Integer ruleIndex = result.getRuleIndex();
    if (ruleIndex == -1 && ref != null) {
      ruleIndex = ref.getIndex();
    }
    if (ruleIndex == -1) {
      return null;
    }
    return ruleIndex;
  }

  private static ReportingDescriptor getReportingDescriptorByIndex(
      final ToolComponent tool, final Integer index, final DescriptorElementOf lookIn) {
    if (lookIn == DescriptorElementOf.RULES) {
      return new ArrayList<>(tool.getRules()).get(index);
    }
    if (lookIn == DescriptorElementOf.NOTIFICATIONS) {
      return new ArrayList<>(tool.getNotifications()).get(index);
    }
    throw new IllegalStateException(lookIn + " cannot find ReportingDescriptor");
  }

  private static Optional<ReportingDescriptor> findReportingDescriptorByGui(
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

  private static Optional<ReportingDescriptor> findReportingDescriptorByRuleId(
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

  private static ToolComponent getToolComponentByIndex(final Run run, final Integer index) {
    return new ArrayList<>(run.getTool().getExtensions()).get(index);
  }

  private static ToolComponent getToolComponentByGui(final Run run, final String guid) {
    return run.getTool().getExtensions().stream()
        .filter((it) -> it.getGuid() != null && it.getGuid().equals(guid))
        .findFirst()
        .get();
  }
}
