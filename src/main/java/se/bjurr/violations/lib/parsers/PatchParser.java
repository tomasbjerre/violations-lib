package se.bjurr.violations.lib.parsers;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;

public class PatchParser implements ViolationsParser {

	public static final Pattern RANGE_PATTERN =
		      Pattern.compile(
		          "@@\\p{IsWhite_Space}-[0-9]+(?:,[0-9]+)?\\p{IsWhite_Space}\\+([0-9]+)(?:,[0-9]+)?\\p{IsWhite_Space}@@.*");

  @Override
  public Set<Violation> parseReportOutput(
      final String content, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    for (String line : content.split("\n")) {
      int currentLine = 0;
	if (line.startsWith("@")) {
        Matcher matcher = RANGE_PATTERN.matcher(line);
        if (!matcher.matches()) {
          throw new IllegalStateException(
              "Unable to parse patch line " + line + "\nFull patch: \n" + content);
        }
        currentLine  = Integer.parseInt(matcher.group(1));
      } else if (line.startsWith("+") && !line.startsWith("++")) {
        currentLine++;
      } else if (line.startsWith(" ")) {
        currentLine++;
      }
    }
    return violations;
  }
}
