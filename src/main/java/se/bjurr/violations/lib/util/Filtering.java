package se.bjurr.violations.lib.util;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;

import java.util.ArrayList;
import java.util.List;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public final class Filtering {
  private Filtering() {
  }
  public static List<Violation> withAtLEastSeverity(List<Violation> unfiltered, final SEVERITY severity) {
List<Violation> filtered = new ArrayList<>();
for (Violation candidate : unfiltered) {
  if (isSeverer(candidate.getSeverity(),severity)) {
    filtered.add(candidate);
  }
}
return filtered ;
   }

  private static boolean isSeverer(SEVERITY candiate,SEVERITY  atLeast) {
    return candiate == ERROR//
        ||  atLeast == INFO//
        || candiate == WARN && atLeast == WARN;
        }

}
