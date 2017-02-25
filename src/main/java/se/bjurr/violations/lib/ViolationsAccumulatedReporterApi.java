package se.bjurr.violations.lib;

import static se.bjurr.violations.lib.model.SEVERITY.INFO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class ViolationsAccumulatedReporterApi {

  public static ViolationsAccumulatedReporterApi violationsAccumulatedReporterApi() {
    return new ViolationsAccumulatedReporterApi();
  }

  private SEVERITY atLeastSeverity = INFO;
  private ORDERED_BY orderedBy;

  private final List<Violation> violations = new ArrayList<>();

  private ViolationsAccumulatedReporterApi() {}

  public ViolationsAccumulatedReporterApi orderedBy(ORDERED_BY orderedBy) {
    this.orderedBy = orderedBy;
    return this;
  }

  public List<Violation> violations() {
    List<Violation> sorted = new ArrayList<>();
    for (Violation violation : violations) {
      if (violation.getSeverity().ordinal() >= atLeastSeverity.ordinal()) {
        sorted.add(violation);
      }
    }
    Collections.sort(sorted, orderedBy.getComparator());
    return sorted;
  }

  public ViolationsAccumulatedReporterApi withAtLeastSeverity(SEVERITY atLeastSeverity) {
    this.atLeastSeverity = atLeastSeverity;
    return this;
  }

  public ViolationsAccumulatedReporterApi withViolationsReporterApiList(
      List<Violation> violations) {
    this.violations.addAll(violations);
    return this;
  }
}
