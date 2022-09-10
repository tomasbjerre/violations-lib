package se.bjurr.violations.lib.util;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressFBWarnings("REDOS")
public class PatchParserUtil {

  private static final Pattern RANGE_PATTERN =
      Pattern.compile(
          "@@\\p{IsWhite_Space}-([0-9]+)(?:,[0-9]+)?\\p{IsWhite_Space}\\+([0-9]+)(?:,[0-9]+)?\\p{IsWhite_Space}@@.*");

  private final Map<Integer, Optional<Integer>> newLineToOldLineTable;
  private final Map<Integer, Optional<Integer>> newLineToLineInDiffTable;

  private String patchString;

  public PatchParserUtil(final String patchString) {
    this.patchString = patchString;
    this.newLineToOldLineTable = new TreeMap<>();
    this.newLineToLineInDiffTable = new TreeMap<>();
    if (patchString == null) {
      return;
    }
    int currentLine = -1;
    int patchLocation = 1;
    int diffLocation = 0;
    for (final String line : patchString.split("\n")) {
      if (line.startsWith("@")) {
        final Matcher matcher = RANGE_PATTERN.matcher(line);
        if (!matcher.matches()) {
          throw new IllegalStateException(
              "Unable to parse patch line " + line + "\nFull patch: \n" + patchString);
        }
        currentLine = Integer.parseInt(matcher.group(2));
        patchLocation = Integer.parseInt(matcher.group(1));
      } else if (line.startsWith("+") && !line.startsWith("++")) {
        this.newLineToOldLineTable.put(currentLine, empty());
        currentLine++;
      } else if (line.startsWith(" ")) {
        this.newLineToOldLineTable.put(currentLine, of(patchLocation));
        currentLine++;
        patchLocation++;
      } else {
        patchLocation++;
      }
      diffLocation++;
      this.newLineToLineInDiffTable.put(currentLine, of(diffLocation));
    }
  }

  public boolean isLineInDiff(final Integer newLine) {
    return this.newLineToOldLineTable.containsKey(newLine);
  }

  public Optional<Integer> findOldLine(final Integer newLine) {
    if (this.newLineToOldLineTable.containsKey(newLine)) {
      return this.newLineToOldLineTable.get(newLine);
    }
    return empty();
  }

  public Optional<Integer> findLineInDiff(final int newLine) {
    if (this.newLineToLineInDiffTable.containsKey(newLine)) {
      return this.newLineToLineInDiffTable.get(newLine);
    }
    return empty();
  }

  Map<Integer, Optional<Integer>> getNewLineToOldLineTable() {
    return this.newLineToOldLineTable;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("patch:\n");
    this.withLines(sb, this.patchString);
    sb.append("\n\n");
    sb.append("newLineToLineInDiffTable:\n");
    this.toString(sb, this.newLineToLineInDiffTable);
    sb.append("\n\nnewLineToOldLineTable:\n");
    this.toString(sb, this.newLineToOldLineTable);
    return sb.toString();
  }

  private void withLines(final StringBuilder sb, final String str) {
    final String[] lines = str.split("\n");
    for (int i = 0; i < lines.length; i++) {
      sb.append(i + "\t: " + lines[i] + "\n");
    }
  }

  private void toString(final StringBuilder sb, final Map<Integer, Optional<Integer>> map) {
    for (final Map.Entry<Integer, Optional<Integer>> e : map.entrySet()) {
      sb.append(e.getKey() + " : " + e.getValue().orElse(null) + "\n");
    }
  }
}
