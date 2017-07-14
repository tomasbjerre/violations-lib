package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerAttribute;
import static se.bjurr.violations.lib.reports.Parser.PMD;

import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.Optional;

public class PMDParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    List<Violation> violations = new ArrayList<>();
    List<String> files = getChunks(string, "<file", "</file>");
    for (String fileChunk : files) {
      String filename = getAttribute(fileChunk, "name");
      List<String> violationsChunks = getChunks(fileChunk, "<violation", "</violation>");
      for (String violationChunk : violationsChunks) {
        Integer beginLine = getIntegerAttribute(violationChunk, "beginline");
        Integer endLine = getIntegerAttribute(violationChunk, "endline");
        Optional<Integer> beginColumn = findIntegerAttribute(violationChunk, "begincolumn");
        String rule = getAttribute(violationChunk, "rule").trim();
        String ruleSet = getAttribute(violationChunk, "ruleset").trim();
        String externalInfoUrl = getAttribute(violationChunk, "externalInfoUrl").trim();
        Integer priority = getIntegerAttribute(violationChunk, "priority");
        SEVERITY severity = toSeverity(priority);

        violations.add( //
            violationBuilder() //
                .setParser(PMD) //
                .setStartLine(beginLine) //
                .setEndLine(endLine) //
                .setColumn(beginColumn.orNull()) //
                .setFile(filename) //
                .setSeverity(severity) //
                .setRule(rule) //
                .setMessage(ruleSet + " " + externalInfoUrl) //
                .build() //
            );
      }
    }
    return violations;
  }

  public SEVERITY toSeverity(Integer priority) {
    if (priority < 3) {
      return ERROR;
    }
    if (priority < 5) {
      return WARN;
    }
    return INFO;
  }
}
