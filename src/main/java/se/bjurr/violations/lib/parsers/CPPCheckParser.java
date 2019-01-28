package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CPPCHECK;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        final Optional<String> info = findAttribute(locationChunk, "info");
        String fileString = getAttribute(errorChunk, "file");
        String message = "";
        if (verbose.startsWith(msg)) {
          message = verbose;
        } else {
          message = msg + ". " + verbose;
        }
        if (info.isPresent() && !message.contains(info.get())) {
          message = message + ". " + info.get();
        }
        violations.add( //
            violationBuilder() //
                .setParser(CPPCHECK) //
                .setStartLine(line) //
                .setFile(fileString) //
                .setSeverity(toSeverity(severity)) //
                .setMessage(message) //
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
