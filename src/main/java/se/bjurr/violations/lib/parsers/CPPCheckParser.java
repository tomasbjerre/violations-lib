package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerAttribute;
import static se.bjurr.violations.lib.reports.Reporter.CPPCHECK;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class CPPCheckParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    List<Violation> violations = new ArrayList<>();
    List<String> errorChunks = getChunks(string, "<error", "</error>");
    for (String errorChunk : errorChunks) {
      String severity = getAttribute(errorChunk, "severity");
      String msg = getAttribute(errorChunk, "msg");
      String verbose = getAttribute(errorChunk, "verbose");
      String id = getAttribute(errorChunk, "id");
      List<String> locationChunks = getChunks(errorChunk, "<location", "/>");
      for (String locationChunk : locationChunks) {
        Integer line = getIntegerAttribute(locationChunk, "line");
        String fileString = getAttribute(errorChunk, "file");
        violations.add( //
            violationBuilder() //
                .setReporter(CPPCHECK) //
                .setStartLine(line) //
                .setFile(fileString) //
                .setSeverity(toSeverity(severity)) //
                .setMessage(msg + ". " + verbose) //
                .setRule(id) //
                .build() //
            );
      }
    }
    return violations;
  }

  public SEVERITY toSeverity(String severity) {
    if (severity.equalsIgnoreCase("error")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("warning")) {
      return WARN;
    }
    return INFO;
  }
}
