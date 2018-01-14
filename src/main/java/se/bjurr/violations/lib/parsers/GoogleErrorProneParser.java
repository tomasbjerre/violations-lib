package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.GOOGLEERRORPRONE;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class GoogleErrorProneParser implements ViolationsParser {
  private static Pattern NEW_VIOLATION =
      Pattern.compile("^((:[^/]*)|([^:]))([^:]+?):([^:]+?):([^:]+?):[^\\[]*\\[([^\\]]+?)](.*)");

  @Override
  public List<Violation> parseReportOutput(final String reportContent) throws Exception {
    final List<Violation> found = new ArrayList<>();
    String currentFilename = null;
    Integer currentLine = null;
    SEVERITY currentSeverity = null;
    String currentRule = null;
    String currentRuleMessage = null;
    StringBuilder currentMessage = null;
    final String[] lines = reportContent.split("\n");
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i];
      final Matcher matcher = NEW_VIOLATION.matcher(line);
      if (matcher.find()) {
        currentFilename = matcher.group(4).trim();
        currentLine = Integer.parseInt(matcher.group(5));
        currentSeverity = toSeverity(matcher.group(6));
        currentRule = matcher.group(7).trim();
        currentRuleMessage = matcher.group(8).trim();
        currentMessage = new StringBuilder();
        for (int j = i + 1; j < lines.length; j++) {
          line = lines[j];
          if (!line.startsWith("  ")) {
            found.add(
                violationBuilder() //
                    .setFile(currentFilename) //
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
