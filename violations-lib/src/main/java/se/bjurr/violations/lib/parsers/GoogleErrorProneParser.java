package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.GOOGLEERRORPRONE;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class GoogleErrorProneParser implements ViolationsParser {

  /**
   * Matches paths in the style: path:lineNumber, including relative paths (../a.txt) and absolute
   * Windows paths (C:\\a.txt, C:/a.txt)
   */
  private static final String PATH_REGEX = "(([A-Z]:)?[^:]+?):(\\d+)";

  /** warning: [CheckName] Description of the problem */
  private static final String WARNING_REGEX = "([^:]+?): \\[([^]]+?)] (.*)";

  private static final Pattern NEW_VIOLATION =
      Pattern.compile("^" + PATH_REGEX + ": " + WARNING_REGEX + "$");

  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> found = new TreeSet<>();
    final String[] lines = reportContent.split("\n");
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i];
      final Matcher matcher = NEW_VIOLATION.matcher(line);
      if (matcher.find()) {
        final String currentFilename = matcher.group(1).trim();
        final int currentLine = Integer.parseInt(matcher.group(3));
        final SEVERITY currentSeverity = this.toSeverity(matcher.group(4));
        final String currentRule = matcher.group(5).trim();
        final String currentRuleMessage = matcher.group(6).trim();
        final StringBuilder currentMessage = new StringBuilder();
        for (int j = i + 1; j < lines.length; j++) {
          line = lines[j];
          if (!line.startsWith("  ")) {
            found.add(
                violationBuilder()
                    //
                    .setFile(currentFilename)
                    //
                    .setMessage(currentRuleMessage + "\n\n" + currentMessage.toString()) //
                    .setParser(GOOGLEERRORPRONE) //
                    .setRule(currentRule) //
                    .setSeverity(currentSeverity) //
                    .setStartLine(currentLine) //
                    .build());
            break;
          }
          i++;
          currentMessage.append(line.trim());
        }
      }
    }
    return found;
  }

  private SEVERITY toSeverity(final String from) {
    if (from.trim().equalsIgnoreCase("error")) {
      return ERROR;
    }
    if (from.trim().equalsIgnoreCase("warning")) {
      return WARN;
    }
    return INFO;
  }
}
