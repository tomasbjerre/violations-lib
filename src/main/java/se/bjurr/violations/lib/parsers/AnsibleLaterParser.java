package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;

import com.google.gson.Gson;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;

public class AnsibleLaterParser implements ViolationsParser {

  public class AnsibleLaterEntry {
    public String asctime;
    public String levelname;
    public String message;
    public String later_tag;
    public String later_standard;
    public String later_file;
    public boolean later_passed;
    public String later_sid;
    public Integer later_lineno;
    public String later_message;

    public AnsibleLaterEntry() {}
  }

  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    return Arrays.asList(string.split("\\r?\\n|\\r")).stream()
        .filter(it -> !it.trim().isEmpty())
        .map(it -> new Gson().fromJson(it, AnsibleLaterEntry.class))
        .map(
            it ->
                Violation.violationBuilder()
                    .setFile(it.later_file)
                    .setMessage(it.message)
                    .setParser(Parser.ANSIBLELATER)
                    .setRule(it.later_sid)
                    .setStartLine(it.later_lineno == null ? Violation.NO_LINE : it.later_lineno)
                    .setSeverity(this.toSeverity(it.levelname))
                    .build())
        .collect(Collectors.toSet());
  }

  private SEVERITY toSeverity(final String severity) {
    if (severity.equalsIgnoreCase("ERROR")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("WARNING")) {
      return WARN;
    }
    return INFO;
  }
}
