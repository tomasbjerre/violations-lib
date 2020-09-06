package se.bjurr.violations.lib.parsers;

import static com.github.difflib.patch.DeltaType.CHANGE;
import static com.github.difflib.patch.DeltaType.DELETE;
import static com.github.difflib.patch.DeltaType.EQUAL;
import static com.github.difflib.patch.DeltaType.INSERT;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.DIFF;

import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import com.github.difflib.unifieddiff.UnifiedDiff;
import com.github.difflib.unifieddiff.UnifiedDiffFile;
import com.github.difflib.unifieddiff.UnifiedDiffReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;

public class DiffParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(String reportContent, ViolationsLogger violationsLogger)
      throws Exception {
    Set<Violation> violations = new TreeSet<>();
    InputStream reportContentStream = new ByteArrayInputStream(reportContent.getBytes(UTF_8));
    UnifiedDiff parsedDiff = UnifiedDiffReader.parseUnifiedDiff(reportContentStream);
    for (UnifiedDiffFile diffFile : parsedDiff.getFiles()) {
      String file = diffFile.getFromFile();
      Patch<String> patch = diffFile.getPatch();
      for (AbstractDelta<String> delta : patch.getDeltas()) {
        String fromString = toString(delta.getSource().getLines());
        int fromLine = delta.getSource().getPosition();
        String toString = toString(delta.getTarget().getLines());
        int toLine = delta.getTarget().getPosition();

        String message = "";

        if (delta.getType() == CHANGE) {
          message =
              "Change line "
                  + fromLine
                  + ":\n\n"
                  + fromString
                  + "\n\nTo line "
                  + toLine
                  + ":\n\n"
                  + toString;
        } else if (delta.getType() == DELETE) {
          message =
              "Delete line "
                  + fromLine
                  + ":\n\n"
                  + fromString
                  + "\n\nTo line "
                  + toLine
                  + ":\n\n"
                  + toString;
        } else if (delta.getType() == EQUAL) {
          message =
              "Equal line "
                  + fromLine
                  + ":\n\n"
                  + fromString
                  + "\n\nTo line "
                  + toLine
                  + ":\n\n"
                  + toString;
        } else if (delta.getType() == INSERT) {
          message =
              "Insert line "
                  + fromLine
                  + ":\n\n"
                  + fromString
                  + "\n\nTo line "
                  + toLine
                  + ":\n\n"
                  + toString;
        }

        violations.add(
            violationBuilder()
                .setParser(DIFF)
                .setFile(file)
                .setStartLine(fromLine)
                .setMessage(message)
                .setSeverity(ERROR)
                .build());
      }
    }
    return violations;
  }

  private String toString(List<String> lines) {
    return lines.stream().collect(joining("\n")).trim();
  }
}
