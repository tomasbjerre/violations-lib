package se.bjurr.violations.lib.parsers;

import java.util.Set;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;

public interface ViolationsParser {
  Set<Violation> parseReportOutput(String reportContent, ViolationsLogger violationsLogger)
      throws Exception;
}
