package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.CPPCHECK;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class CPPCheckParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    final List<String> errorChunks = getChunks(string, "<error ", "</error>");
    for (int errorIndex = 0; errorIndex < errorChunks.size(); errorIndex++) {
      final String errorChunk = errorChunks.get(errorIndex);
      final String severity = getAttribute(errorChunk, "severity");
      final String msg = getAttribute(errorChunk, "msg");
      final Optional<String> verbose = findAttribute(errorChunk, "verbose");
      final String id = getAttribute(errorChunk, "id");
      final List<String> locationChunks = getChunks(errorChunk, "<location", "/>");

      for (final String locationChunk : locationChunks) {
        final Integer line = getIntegerAttribute(locationChunk, "line");
        final Optional<String> info = findAttribute(locationChunk, "info");
        final String fileString = getAttribute(errorChunk, "file");
        final String message = this.constructMessage(msg, verbose, info);
        violations.add( //
            Violation.violationBuilder() //
                .setParser(CPPCHECK) //
                .setStartLine(line) //
                .setFile(fileString) //
                .setSeverity(this.toSeverity(severity)) //
                .setMessage(message) //
                .setRule(id) //
                .setGroup(Integer.toString(errorIndex)) //
                .build() //
            );
      }
    }

    final List<String> errorChunksNoEndtag = getChunks(string, "<error ", "\\/>");
    for (int errorIndex = 0; errorIndex < errorChunksNoEndtag.size(); errorIndex++) {
      final String errorChunk = errorChunksNoEndtag.get(errorIndex);
      final String severity = getAttribute(errorChunk, "severity");
      final String msg = getAttribute(errorChunk, "msg");
      final Optional<String> verbose = findAttribute(errorChunk, "verbose");
      final String id = getAttribute(errorChunk, "id");
      final Optional<Integer> resultLine = findIntegerAttribute(errorChunk, "line");
      final Optional<String> resultFile = findAttribute(errorChunk, "file");
      final Optional<String> resultInfo = findAttribute(errorChunk, "info");

      if (resultLine.isPresent() && resultFile.isPresent()) {
        final String message = this.constructMessage(msg, verbose, resultInfo);
        violations.add( //
            Violation.violationBuilder() //
                .setParser(CPPCHECK) //
                .setStartLine(resultLine.get()) //
                .setFile(resultFile.get()) //
                .setSeverity(this.toSeverity(severity)) //
                .setMessage(message) //
                .setRule(id) //
                .setGroup(Integer.toString(errorIndex)) //
                .build() //
            );
      }
    }

    return violations;
  }

  private String constructMessage(
      final String msg, final Optional<String> verbose, final Optional<String> info) {
    String message = "";
    if (verbose.orElse("").startsWith(msg)) {
      message = verbose.get();
    } else {
      message = msg + ". " + verbose.orElse("");
    }
    if (info.isPresent() && !message.contains(info.get())) {
      message = message + ". " + info.get();
    }
    return message;
  }

  public SEVERITY toSeverity(final String severity) {
    if (severity.equalsIgnoreCase("error")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("warning")) {
      return WARN;
    }
    return INFO;
  }
}
