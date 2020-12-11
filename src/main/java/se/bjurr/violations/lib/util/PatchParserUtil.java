package se.bjurr.violations.lib.util;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatchParserUtil {

  private static final Pattern RANGE_PATTERN =
      Pattern.compile(
          "@@\\p{IsWhite_Space}-([0-9]+)(?:,[0-9]+)?\\p{IsWhite_Space}\\+([0-9]+)(?:,[0-9]+)?\\p{IsWhite_Space}@@.*");

  private final Map<Integer, Optional<Integer>> newLineToOldLineTable;
  private final Map<Integer, Optional<Integer>> newLineToLineInDiffTable;

  public PatchParserUtil(String patchString) {
    newLineToOldLineTable = new TreeMap<>();
    newLineToLineInDiffTable = new TreeMap<>();
    if (patchString == null) {
      return;
    }
    int currentLine = -1;
    int patchLocation = 1;
    int diffLocation = 0;
    for (String line : patchString.split("\n")) {
      if (line.startsWith("@")) {
        Matcher matcher = RANGE_PATTERN.matcher(line);
        if (!matcher.matches()) {
          throw new IllegalStateException(
              "Unable to parse patch line " + line + "\nFull patch: \n" + patchString);
        }
        currentLine = Integer.parseInt(matcher.group(2));
        patchLocation = Integer.parseInt(matcher.group(1));
      } else if (line.startsWith("+") && !line.startsWith("++")) {
        newLineToOldLineTable.put(currentLine, empty());
        currentLine++;
      } else if (line.startsWith(" ")) {
        newLineToOldLineTable.put(currentLine, of(patchLocation));
        currentLine++;
        patchLocation++;
      } else {
        patchLocation++;
      }
      diffLocation++;
      newLineToLineInDiffTable.put(currentLine, of(diffLocation));
    }
  }

  public boolean isLineInDiff(Integer newLine) {
    return newLineToOldLineTable.containsKey(newLine);
  }

  public Optional<Integer> findOldLine(Integer newLine) {
    if (newLineToOldLineTable.containsKey(newLine)) {
      return newLineToOldLineTable.get(newLine);
    }
    return empty();
  }

  public Optional<Integer> findLineInDiff(final int newLine) {
    if (newLineToLineInDiffTable.containsKey(newLine)) {
      return newLineToLineInDiffTable.get(newLine);
    }
    return empty();
  }

  Map<Integer, Optional<Integer>> getNewLineToOldLineTable() {
    return newLineToOldLineTable;
  }
}
