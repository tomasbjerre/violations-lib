package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.PCLINT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getLines;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getParts;

import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class PCLintParser implements ViolationsParser {

	@Override
	public List<Violation> parseReportOutput(String string) throws Exception {
		List<Violation> violations = new ArrayList<>();
		List<String> lines = getLines(string);
		System.out.println(lines.size());
		Pattern misraPattern = Pattern.compile("\\[MISRA.*\\]");
		for (String line : lines) {
			Matcher misraMatcher = misraPattern.matcher(line);
			if (misraMatcher.find()) {
				parseMisraViolation(line, violations);
			} else {
				parseGeneralViolation(line, violations);
			}
		}
		return violations;
	}

	private void parseMisraViolation(String line, List<Violation> violations) {
		List<String> parts = getParts(line, 
									"^([^\\(]+)\\(", 
									"^([\\d]+)\\): ",
									"^(?:Error|Warning|Info|Note) [\\d]+: ([^\\[]*)",
									"^\\[(.*),",
									"(mandatory|required|advisory)\\]",
									"^(.*)$");
		if (parts.isEmpty()) {
			return;
		}
		String filename = parts.get(0);
		Integer lineNumber = parseInt(parts.get(1));

		String severityString = parts.get(4);
		SEVERITY severity = toMisraSeverity(severityString);
		String rule = parts.get(3) + ", " + severityString;
		String message = parts.get(2) + " " + parts.get(5);
		violations.add( //
				violationBuilder() //
						.setParser(PCLINT) //
						.setStartLine(lineNumber) //
						.setFile(filename) //
						.setRule(rule) //
						.setSeverity(severity) //
						.setMessage(message) //
						.build() //
		);
	}

	private void parseGeneralViolation(String line, List<Violation> violations) {
		List<String> parts = getParts(line, 
									"^([^\\(]+)\\(", 
									"^([\\d]+)\\): ", 
									"^(Error|Warning|Info|Note) ",
									"^([\\d]+): ", "^(.*)$");
		if (parts.isEmpty()) {
			return;
		}
		String filename = parts.get(0);
		Integer lineNumber = parseInt(parts.get(1));
		SEVERITY severity = toSeverity(parts.get(2));
		String rule = parts.get(3);
		String message = parts.get(4);
		violations.add( //
				violationBuilder() //
						.setParser(PCLINT) //
						.setStartLine(lineNumber) //
						.setFile(filename) //
						.setRule(rule) //
						.setSeverity(severity) //
						.setMessage(message) //
						.build() //
		);
	}

	private SEVERITY toSeverity(String severity) {
		if (severity.equals("Error")) {
			return ERROR;
		}
		if (severity.equals("Warning")) {
			return WARN;
		}
		return INFO;
	}
	
	private SEVERITY toMisraSeverity(String severity) {
		if (severity.equals("mandatory")) {
			return ERROR;
		}
		if (severity.equals("required")) {
			return WARN;
		}
		return INFO;
	}
}
