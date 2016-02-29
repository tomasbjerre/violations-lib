package se.bjurr.violations.lib;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Ordering.from;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;

import java.util.List;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

import com.google.common.base.Predicate;

public class ViolationsAccumulatedReporterApi {

 private final List<Violation> violations = newArrayList();
 private SEVERITY atLeastSeverity = INFO;
 private ORDERED_BY orderedBy;

 private ViolationsAccumulatedReporterApi() {
 }

 public ViolationsAccumulatedReporterApi withViolationsReporterApiList(List<Violation> violations) {
  this.violations.addAll(violations);
  return this;
 }

 public static ViolationsAccumulatedReporterApi violationsAccumulatedReporterApi() {
  return new ViolationsAccumulatedReporterApi();
 }

 public ViolationsAccumulatedReporterApi withAtLeastSeverity(SEVERITY atLeastSeverity) {
  this.atLeastSeverity = atLeastSeverity;
  return this;
 }

 public ViolationsAccumulatedReporterApi orderedBy(ORDERED_BY orderedBy) {
  this.orderedBy = orderedBy;
  return this;
 }

 public List<Violation> violations() {
  return from(orderedBy.getComparator())//
    .sortedCopy(//
      filter(violations, new Predicate<Violation>() {
       @Override
       public boolean apply(Violation input) {
        return input.getSeverity().ordinal() >= atLeastSeverity.ordinal();
       }
      }));
 }
}
