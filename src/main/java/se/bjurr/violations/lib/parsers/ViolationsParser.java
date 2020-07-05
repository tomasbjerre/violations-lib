package se.bjurr.violations.lib.parsers;

import java.util.List;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;

public interface ViolationsParser {
  List<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception;
}
