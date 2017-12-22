package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.DOCFX;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.HTMLUtils;

public class DocFXParser implements ViolationsParser {
  private static Logger LOG = Logger.getLogger(DocFXParser.class.getName());

  @Override
  public List<Violation> parseReportOutput(String reportContent) throws Exception {
    final List<Violation> violations = new ArrayList<>();

    final String[] lines = reportContent.split("\\r?\\n");
    for (final String rawLineToParse : lines) {
      @SuppressWarnings("unchecked")
      final Map<String, Object> parsedMap = new Gson().fromJson(rawLineToParse, Map.class);
      ;
      final String message = (String) parsedMap.get("message");
      final String messageSeverity = (String) parsedMap.get("message_severity");
      final String fileEncoded = (String) parsedMap.get("file");
      if (fileEncoded == null) {
        LOG.log(Level.FINE, "Ignoring " + rawLineToParse + " because there is not file attribute");
        continue;
      }
      final String file = HTMLUtils.htmlDecode(fileEncoded);
      final String code = (String) parsedMap.get("code");
      final String lineString = (String) parsedMap.get("line");
      Integer lineInt;
      if (lineString == null) {
        lineInt = 0;
      } else {
        lineInt = Integer.parseInt(lineString);
      }
      final String source = (String) parsedMap.get("source");
      final SEVERITY severity = getSeverity(messageSeverity);
      violations.add(
          violationBuilder() //
              .setFile(file) //
              .setMessage(message) //
              .setParser(DOCFX) //
              .setRule(code) //
              .setSeverity(severity) //
              .setStartLine(lineInt) //
              .setSpecific("source", source)
              .build());
    }

    return violations;
  }

  private SEVERITY getSeverity(String messageSeverity) {
    if (messageSeverity.equalsIgnoreCase("Error")) {
      return SEVERITY.ERROR;
    }
    if (messageSeverity.equalsIgnoreCase("Warning")) {
      return SEVERITY.WARN;
    }
    return SEVERITY.INFO;
  }
}
