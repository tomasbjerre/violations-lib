package se.bjurr.violations.lib.parsers;

import static java.nio.charset.StandardCharsets.UTF_8;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CPPCHECK;
import static se.bjurr.violations.lib.util.ViolationParserUtils.createXmlReader;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.Utils;

public class CPPCheckParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    try (InputStream input = new ByteArrayInputStream(reportContent.getBytes(UTF_8))) {

      final XMLStreamReader xmlr = createXmlReader(input);
      SEVERITY severity = null;
      String msg = null;
      Optional<String> verbose = null;
      String id = null;
      int errorIndex = -1;
      boolean violationAddedFromError = false;
      String message = null;
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == XMLStreamConstants.END_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("error")) {
            if (!violationAddedFromError) {
              final Violation violation =
                  violationBuilder() //
                      .setParser(CPPCHECK) //
                      .setStartLine(0) //
                      .setFile(Violation.NO_FILE) //
                      .setSeverity(severity) //
                      .setMessage(message) //
                      .setRule(id) //
                      .setGroup(Integer.toString(errorIndex)) //
                      .build();
              violations.add(violation);
            }
          }
        }
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("error")) {
            violationAddedFromError = false;
            errorIndex++;
            final String severityStr = getAttribute(xmlr, "severity");
            severity = this.toSeverity(severityStr);
            msg = getAttribute(xmlr, "msg");
            verbose = findAttribute(xmlr, "verbose");
            id = getAttribute(xmlr, "id");

            final Optional<String> resultFile = findAttribute(xmlr, "file");
            final Optional<Integer> resultLine = findIntegerAttribute(xmlr, "line");
            final Optional<Integer> resultColumn = findIntegerAttribute(xmlr, "column");
            final Optional<String> resultInfo = findAttribute(xmlr, "info");
            message = this.constructMessage(msg, verbose, resultInfo);
            if (resultFile.isPresent() && resultLine.isPresent()) {
              final Violation violation =
                  violationBuilder() //
                      .setParser(CPPCHECK) //
                      .setStartLine(resultLine.get()) //
                      .setColumn(resultColumn.orElse(0)) //
                      .setFile(resultFile.get()) //
                      .setSeverity(severity) //
                      .setMessage(message) //
                      .setRule(id) //
                      .setGroup(Integer.toString(errorIndex)) //
                      .build();
              violations.add(violation);
              violationAddedFromError = true;
            }
          } else if (xmlr.getLocalName().equalsIgnoreCase("location")) {
            final Integer line = getIntegerAttribute(xmlr, "line");
            final Optional<Integer> column = findIntegerAttribute(xmlr, "column");
            final Optional<String> info = findAttribute(xmlr, "info");
            message = this.constructMessage(msg, verbose, info);
            String file = findAttribute(xmlr, "file").orElse("");
            file = Utils.firstNonNull(Utils.emptyToNull(file), Violation.NO_FILE);
            final Violation v =
                violationBuilder() //
                    .setParser(CPPCHECK) //
                    .setMessage(message) //
                    .setStartLine(line) //
                    .setColumn(column.orElse(null)) //
                    .setFile(file) //
                    .setSeverity(severity) //
                    .setRule(id) //
                    .setGroup(Integer.toString(errorIndex)) //
                    .build();
            violations.add(v);
            violationAddedFromError = true;
          }
        }
      }
    }

    return violations;
  }

  private String constructMessage(
      final String msg, final Optional<String> verbose, final Optional<String> info) {
    String message = "";
    if (verbose.orElse("").startsWith(msg)) {
      message = verbose.get();
    } else {
      message = msg + ". " + verbose.orElse("");
    }
    if (info.isPresent() && !message.contains(info.get())) {
      message = message + ". " + info.get();
    }
    return message;
  }

  public SEVERITY toSeverity(final String severity) {
    if (severity.equalsIgnoreCase("error")) {
      return ERROR;
    }
    if (severity.equalsIgnoreCase("warning")) {
      return WARN;
    }
    return INFO;
  }
}
