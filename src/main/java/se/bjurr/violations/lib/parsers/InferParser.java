package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Reporter.INFER;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class InferParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    List<Violation> violations = new ArrayList<>();

    List<String> lines = asList(string.split("\n"));

    String firstLine = lines.get(0);
    List<String> fields = asList(firstLine.split(","));

    for (int line = 1; line < lines.size(); line++) {
      List<String> lineFields = asList(lines.get(line).split(","));
      Violation violation =
          violationBuilder() //
              .setReporter(INFER) //
              .setFile(getValue(fields, lineFields, "file")) //
              .setMessage(getValue(fields, lineFields, "qualifier")) //
              .setRule(getValue(fields, lineFields, "type")) //
              .setSeverity(toSeverity(getValue(fields, lineFields, "severity"))) //
              .setStartLine(parseInt(getValue(fields, lineFields, "line"))) //
              .build();
      violations.add(violation);
    }

    return violations;
  }

  private SEVERITY toSeverity(String string) {
    if (string.equalsIgnoreCase("HIGH")) {
      return ERROR;
    }
    if (string.equalsIgnoreCase("MEDIUM")) {
      return WARN;
    }
    return INFO;
  }

  private String getValue(List<String> fields, List<String> lineFields, String fieldName) {
    String value = lineFields.get(fields.indexOf(fieldName));
    if (value.startsWith("\"")) {
      return value.substring(1, value.length() - 1);
    }
    return value;
  }
}
