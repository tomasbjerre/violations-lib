package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;

import java.util.Set;
import java.util.TreeSet;

public class GenericParser implements ViolationsParser{
    @Override
    public Set<Violation> parseReportOutput(String reportContent, ViolationsLogger violationsLogger) throws Exception {
        final Set<Violation> violations = new TreeSet<>();
        violations.add(
                violationBuilder()
                        .setParser(Parser.GENERIC)
                        .setFile("Generic Comment")
                        .setStartLine(0)
                        .setColumn(0)
                        .setSeverity(SEVERITY.INFO)
                        .setMessage(reportContent)
                        .build()
        );
        return violations;
    }
}
