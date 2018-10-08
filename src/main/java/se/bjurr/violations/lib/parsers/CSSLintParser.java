package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CSSLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getChunks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class CSSLintParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    List<Violation> violations = new ArrayList<>();
    List<String> files = getChunks(string, "<file", "</file>");
    for (String fileChunk : files) {
      String filename = getAttribute(fileChunk, "name");
      List<String> issues = getChunks(fileChunk, "<issue", "/>");
      for (String issueChunk : issues) {
        Integer line = findIntegerAttribute(issueChunk, "line").orElse(1);
        Optional<Integer> charAttrib = findIntegerAttribute(issueChunk, "char");
        String severity = getAttribute(issueChunk, "severity");

        String message = getAttribute(issueChunk, "reason");
        String evidence = findAttribute(issueChunk, "evidence").orElse("").trim();
        violations.add( //
            violationBuilder() //
                .setParser(CSSLINT) //
                .setStartLine(line) //
                .setColumn(charAttrib.orElse(null)) //
                .setFile(filename) //
                .setSeverity(toSeverity(severity)) //
                .setMessage(message + ": " + evidence) //
                .build() //
            );
      }
    }
    return violations;
  }

  public SEVERITY toSeverity(String severity) {
    if (severity.equalsIgnoreCase("ERROR")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("WARNING")) {
      return WARN;
    }
    return INFO;
  }
}
