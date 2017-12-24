package se.bjurr.violations.lib;

import static se.bjurr.violations.lib.ViolationsReporterDetailLevel.COMPACT;
import static se.bjurr.violations.lib.ViolationsReporterDetailLevel.PER_FILE_COMPACT;
import static se.bjurr.violations.lib.ViolationsReporterDetailLevel.VERBOSE;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.util.Utils.checkNotNull;

import com.jakewharton.fliptables.FlipTable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class ViolationsReporterApi {

  private Iterable<Violation> violations;

  private ViolationsReporterApi() {}

  public static ViolationsReporterApi violationsReporterApi() {
    return new ViolationsReporterApi();
  }

  public String getReport(ViolationsReporterDetailLevel level) {
    checkNotNull(violations, "violations");

    final StringBuilder sb = new StringBuilder();

    if (level == COMPACT) {
      sb.append(toCompact(violations, "Summary"));
    } else if (level == PER_FILE_COMPACT) {
      sb.append(toPerFile(violations));
      sb.append(toCompact(violations, "Summary"));
    } else if (level == VERBOSE) {
      sb.append(toVerbose(violations));
      sb.append(toCompact(violations, "Summary"));
    }

    return sb.toString();
  }

  private StringBuilder toVerbose(Iterable<Violation> violations) {
    final StringBuilder sb = new StringBuilder();
    final Map<String, Set<Violation>> perFile = getViolationsPerFile(violations);
    for (final String file : perFile.keySet()) {
      sb.append(file + "\n");
      sb.append(toDetailed(perFile.get(file), "Summary of " + file));
      sb.append("\n");
    }
    return sb;
  }

  private StringBuilder toPerFile(Iterable<Violation> violations) {
    final StringBuilder sb = new StringBuilder();
    final Map<String, Set<Violation>> perFile = getViolationsPerFile(violations);
    for (final String file : perFile.keySet()) {
      sb.append(toCompact(perFile.get(file), "Summary of " + file));
      sb.append("\n");
    }
    return sb;
  }

  public ViolationsReporterApi withViolations(List<Violation> violations) {
    this.violations = violations;
    return this;
  }

  private StringBuilder toDetailed(Iterable<Violation> violations, String summarySubject) {
    final StringBuilder sb = new StringBuilder();
    final List<String[]> rows = new ArrayList<>();
    for (final Violation violation : violations) {
      final String[] row = {
        violation.getReporter(),
        violation.getRule().or(""),
        violation.getSeverity().name(),
        violation.getStartLine().toString(),
        violation.getMessage()
      };
      rows.add(row);
    }

    final String[] headers = {"Reporter", "Rule", "Severity", "Line", "Message"};

    final String[][] data = rows.toArray(new String[][] {});
    sb.append(FlipTable.of(headers, data));
    sb.append("\n");
    sb.append(toCompact(violations, summarySubject));
    sb.append("\n");
    return sb;
  }

  private StringBuilder toCompact(Iterable<Violation> violations, String subject) {
    final StringBuilder sb = new StringBuilder();
    final Map<String, Set<Violation>> perReporter = getViolationsPerReporter(violations);
    final List<String[]> rows = new ArrayList<>();

    Integer totNumInfo = 0;
    Integer totNumWarn = 0;
    Integer totNumError = 0;
    Integer totNumTot = 0;

    for (final String reporter : perReporter.keySet()) {
      final Map<SEVERITY, Set<Violation>> perSeverity =
          getViolationsPerSeverity(perReporter.get(reporter));
      final Integer numInfo = perSeverity.get(INFO).size();
      final Integer numWarn = perSeverity.get(WARN).size();
      final Integer numError = perSeverity.get(ERROR).size();
      final Integer numTot = numInfo + numWarn + numError;
      final String[] row = {
        reporter, numInfo.toString(), numWarn.toString(), numError.toString(), numTot.toString()
      };
      rows.add(row);

      totNumInfo += numInfo;
      totNumWarn += numWarn;
      totNumError += numError;
      totNumTot += numTot;
    }
    final String[] row = {
      "", totNumInfo.toString(), totNumWarn.toString(), totNumError.toString(), totNumTot.toString()
    };
    rows.add(row);

    final String[] headers = {"Reporter", INFO.name(), WARN.name(), ERROR.name(), "Total"};

    final String[][] data = rows.toArray(new String[][] {});
    sb.append(subject + "\n");
    sb.append(FlipTable.of(headers, data));
    return sb;
  }

  private Map<SEVERITY, Set<Violation>> getViolationsPerSeverity(Set<Violation> violations) {
    final Map<SEVERITY, Set<Violation>> violationsPerSeverity = new TreeMap<>();
    for (final SEVERITY severity : SEVERITY.values()) {
      violationsPerSeverity.put(severity, new TreeSet<Violation>());
    }

    for (final Violation violation : violations) {
      final Set<Violation> perReporter =
          getOrCreate(violationsPerSeverity, violation.getSeverity());
      perReporter.add(violation);
    }
    return violationsPerSeverity;
  }

  private Map<String, Set<Violation>> getViolationsPerFile(Iterable<Violation> violations) {
    final Map<String, Set<Violation>> violationsPerFile = new TreeMap<>();
    for (final Violation violation : violations) {
      final Set<Violation> perReporter = getOrCreate(violationsPerFile, violation.getFile());
      perReporter.add(violation);
    }
    return violationsPerFile;
  }

  private Map<String, Set<Violation>> getViolationsPerReporter(Iterable<Violation> violations) {
    final Map<String, Set<Violation>> violationsPerReporter = new TreeMap<>();
    for (final Violation violation : violations) {
      final Set<Violation> perReporter =
          getOrCreate(violationsPerReporter, violation.getReporter());
      perReporter.add(violation);
    }
    return violationsPerReporter;
  }

  private <T, K> Set<T> getOrCreate(Map<K, Set<T>> container, K key) {
    if (container.containsKey(key)) {
      return container.get(key);
    } else {
      final Set<T> violationList = new TreeSet<>();
      container.put(key, violationList);
      return violationList;
    }
  }
}
