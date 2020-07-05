package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.PITEST;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getContent;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerContent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;

public class PiTestParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    final String mutations = getContent(string, "mutations");
    final List<String> mutationChunks = getChunks(mutations, "<mutation", "</mutation>");
    for (final String mutationChunk : mutationChunks) {
      final String mutatedClass = getContent(mutationChunk, "mutatedClass");
      final String sourceFile = getContent(mutationChunk, "sourceFile");
      final String filename = this.toFilename(mutatedClass, sourceFile);
      final String status = getAttribute(mutationChunk, "status");
      final String detected = getAttribute(mutationChunk, "detected");
      final String mutatedMethod = getContent(mutationChunk, "mutatedMethod");
      final String methodDescription = getContent(mutationChunk, "methodDescription");
      final String mutator = getContent(mutationChunk, "mutator");
      final String message = status + ", " + mutator + ", " + methodDescription;
      final Integer startLine = getIntegerContent(mutationChunk, "lineNumber");
      final Integer index = getIntegerContent(mutationChunk, "index");
      final Map<String, String> specifics = new HashMap<>();
      specifics.put("detected", detected);
      specifics.put("mutatedMethod", mutatedMethod);
      specifics.put("mutatedClass", mutatedClass);
      specifics.put("status", status);
      specifics.put("methodDescription", methodDescription);
      violations.add( //
          violationBuilder() //
              .setRule(mutator) //
              .setSource(sourceFile) //
              .setParser(PITEST) //
              .setStartLine(startLine) //
              .setColumn(index) //
              .setFile(filename) //
              .setSeverity(WARN) //
              .setMessage(message) //
              .setSpecifics(specifics) //
              .build() //
          );
    }
    return violations;
  }

  /** Use package from mutadedClass and assume sourceFile is in that package. */
  private String toFilename(final String mutatedClass, final String sourceFile) {
    return mutatedClass.substring(0, mutatedClass.lastIndexOf(".")).replaceAll("\\.", "/")
        + "/"
        + sourceFile;
  }
}
