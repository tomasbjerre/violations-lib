package se.bjurr.violations.lib.util;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;

import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public final class Filtering {
  private Filtering() {}

  public static Set<Violation> withAtLEastSeverity(
      final Set<Violation> unfiltered, final SEVERITY severity) {
    final Set<Violation> filtered = new TreeSet<>();
    for (final Violation candidate : unfiltered) {
      if (isSeverer(candidate.getSeverity(), severity)) {
        filtered.add(candidate);
      }
    }
    return filtered;
  }

  private static boolean isSeverer(final SEVERITY candiate, final SEVERITY atLeast) {
    return candiate == ERROR //
        || atLeast == INFO //
        || candiate == WARN && atLeast == WARN;
  }
}
