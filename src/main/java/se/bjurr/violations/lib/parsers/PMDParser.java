package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.PMD;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getContent;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class PMDParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    final List<String> files = getChunks(string, "<file", "</file>");
    for (final String fileChunk : files) {
      final String filename = getAttribute(fileChunk, "name");
      final List<String> violationsChunks = getChunks(fileChunk, "<violation", "</violation>");
      for (final String violationChunk : violationsChunks) {
        final Integer beginLine = getIntegerAttribute(violationChunk, "beginline");
        final Integer endLine = getIntegerAttribute(violationChunk, "endline");
        final Optional<Integer> beginColumn = findIntegerAttribute(violationChunk, "begincolumn");
        final String rule = getAttribute(violationChunk, "rule").trim();
        final Optional<String> ruleSetOpt = findAttribute(violationChunk, "ruleset");
        final Optional<String> externalInfoUrlOpt =
            findAttribute(violationChunk, "externalInfoUrl");
        final Integer priority = getIntegerAttribute(violationChunk, "priority");
        final SEVERITY severity = toSeverity(priority);
        final String content = getContent(violationChunk, "violation");
        final String message =
            content + "\n\n" + ruleSetOpt.orElse("") + " " + externalInfoUrlOpt.orElse("");
        violations.add( //
            violationBuilder() //
                .setParser(PMD) //
                .setStartLine(beginLine) //
                .setEndLine(endLine) //
                .setColumn(beginColumn.orElse(null)) //
                .setFile(filename) //
                .setSeverity(severity) //
                .setRule(rule) //
                .setCategory(ruleSetOpt.orElse(null))
                .setMessage(message.trim()) //
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
