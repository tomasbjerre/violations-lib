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
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class ViolationsReporterApi {
  private static final int NO_MAX_LINE_LENGTH = -1;
  /** Allow up to 100 characters for the error message if no max line length is specified. */
  private static final int DEFAULT_MAX_MESSAGE_LENGTH = 100;

  private Iterable<Violation> violations;
  private int maxLineLength = NO_MAX_LINE_LENGTH;

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
    for (final Entry<String, Set<Violation>> perFileEntry : perFile.entrySet()) {
      final String file = perFileEntry.getKey();
      final Set<Violation> fileViolations = perFile.get(file);
      sb.append(file + "\n");
      sb.append(toDetailed(fileViolations, "Summary of " + file));
      sb.append("\n");
    }
    return sb;
  }

  private StringBuilder toPerFile(Iterable<Violation> violations) {
    final StringBuilder sb = new StringBuilder();
    final Map<String, Set<Violation>> perFile = getViolationsPerFile(violations);
    for (final Entry<String, Set<Violation>> fileEntry : perFile.entrySet()) {
      final Set<Violation> fileViolations = fileEntry.getValue();
      final String fileName = fileEntry.getKey();
      sb.append(toCompact(fileViolations, "Summary of " + fileName));
      sb.append("\n");
    }
    return sb;
  }

  /**
   * Adds line breaks to the Message column of the report to ensure the table's lines are less than
   * or equal to the given limit.
   *
   * <p>If the other columns and whitespace are are greater than or equal to the requested maximum
   * line length, the Message column will always print at least a single character per line, even if
   * doing so causes the table to exceed the requested length.
   *
   * @param maxLineLength The requested maximum line length.
   * @return This object.
   */
  public ViolationsReporterApi withMaxLineLength(int maxLineLength) {
    if (maxLineLength <= 0) {
      throw new IllegalArgumentException("maxLineLength must be > 0, but given: " + maxLineLength);
    }
    this.maxLineLength = maxLineLength;
    return this;
  }

  public ViolationsReporterApi withViolations(List<Violation> violations) {
    this.violations = violations;
    return this;
  }

  private StringBuilder toDetailed(Iterable<Violation> violations, String summarySubject) {
    final StringBuilder sb = new StringBuilder();
    final List<String[]> rows = new ArrayList<>();

    int longestMessageLineLengthLength = -1;
    for (final Violation violation : violations) {
      final String[] row = {
        violation.getReporter(),
        violation.getRule().or(""),
        violation.getSeverity().name(),
        violation.getStartLine().toString(),
        violation.getMessage(),
      };
      for (String substring : violation.getMessage().split("\n")) {
        longestMessageLineLengthLength =
            Math.max(longestMessageLineLengthLength, substring.length());
      }
      rows.add(row);
    }

    final String[] headers = {"Reporter", "Rule", "Severity", "Line", "Message"};
    final String[][] data = rows.toArray(new String[][] {});

    final int maximumMessageLength;
    if (maxLineLength == NO_MAX_LINE_LENGTH) {
      maximumMessageLength = DEFAULT_MAX_MESSAGE_LENGTH;
    } else {
      final String infiniteLengthTable = FlipTable.of(headers, data);
      final int tableLineLength = infiniteLengthTable.indexOf("\n");
      final int nonMessageLength = tableLineLength - longestMessageLineLengthLength;
      maximumMessageLength = Math.max(1, maxLineLength - nonMessageLength);
    }
    for (String[] row : data) {
      row[4] = addNewlines(row[4], maximumMessageLength);
    }

    sb.append(FlipTable.of(headers, data));
    sb.append("\n");
    sb.append(toCompact(violations, summarySubject));
    sb.append("\n");
    return sb;
  }

  private String addNewlines(String message, int maxLineLength) {
    if (message == null) {
      return "";
    }
    int noLineCounter = 0;
    final StringBuilder withNewlines = new StringBuilder();
    for (int i = 0; i < message.length(); i++) {
      final char charAt = message.charAt(i);
      withNewlines.append(charAt);
      if (charAt == '\n') {
        noLineCounter = 0;
      } else {
        noLineCounter++;
      }
      if (noLineCounter > 0 && noLineCounter % maxLineLength == 0) {
        withNewlines.append('\n');
        noLineCounter = 0;
      }
    }
    return withNewlines.toString().trim();
  }

  private StringBuilder toCompact(Iterable<Violation> violations, String subject) {
    final StringBuilder sb = new StringBuilder();
    final Map<String, Set<Violation>> perReporter = getViolationsPerReporter(violations);
    final List<String[]> rows = new ArrayList<>();

    Integer totNumInfo = 0;
    Integer totNumWarn = 0;
    Integer totNumError = 0;
    Integer totNumTot = 0;

    for (final Entry<String, Set<Violation>> reporterEntry : perReporter.entrySet()) {
      final String reporter = reporterEntry.getKey();
      final Set<Violation> reporterViolations = reporterEntry.getValue();
      final Map<SEVERITY, Set<Violation>> perSeverity =
          getViolationsPerSeverity(reporterViolations);
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
