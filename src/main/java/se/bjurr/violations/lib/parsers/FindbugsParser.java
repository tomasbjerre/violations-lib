package se.bjurr.violations.lib.parsers;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.logging.Level.SEVERE;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.FINDBUGS;
import static se.bjurr.violations.lib.util.Utils.isNullOrEmpty;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.Utils;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class FindbugsParser implements ViolationsParser {
  /** Severity rank. */
  public static final String FINDBUGS_SPECIFIC_RANK = "RANK";

  private static String findbugsMessagesXml;
  private static String findSecurityBugsMessagesXml;

  public static void setFindbugsMessagesXml(final String findbugsMessagesXml) {
    FindbugsParser.findbugsMessagesXml = findbugsMessagesXml;
  }

  public static void setFindSecurityBugsMessagesXml(final String findSecurityBugsMessagesXml) {
    FindbugsParser.findSecurityBugsMessagesXml = findSecurityBugsMessagesXml;
  }

  private Map<String, String> getMessagesPerType(
      final ViolationsLogger violationsLogger, final String messagesXml) throws Exception {
    final Map<String, String> messagesPerType = new HashMap<>();
    try {

      try (InputStream input = new ByteArrayInputStream(messagesXml.getBytes(UTF_8))) {
        final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);
        String type = "";
        String shortDescription = "";
        String details = "";
        while (xmlr.hasNext()) {
          final int eventType = xmlr.next();
          if (eventType == XMLStreamConstants.START_ELEMENT) {
            if (xmlr.getLocalName().equalsIgnoreCase("BugPattern")) {
              type = getAttribute(xmlr, "type");
            }
            if (xmlr.getLocalName().equalsIgnoreCase("ShortDescription")) {
              shortDescription = xmlr.getElementText();
            }
            if (xmlr.getLocalName().equalsIgnoreCase("Details")) {
              details = xmlr.getElementText();
            }
          }
          if (eventType == XMLStreamConstants.END_ELEMENT) {
            if (xmlr.getLocalName().equalsIgnoreCase("BugPattern")) {
              messagesPerType.put(type, shortDescription + "\n\n" + details);
            }
          }
        }
      }
    } catch (final IOException e) {
      violationsLogger.log(SEVERE, e.getMessage(), e);
    }
    return messagesPerType;
  }

  private void parseBugInstance(
      final XMLStreamReader xmlr,
      final Set<Violation> violations,
      final Map<String, String> messagesPerType)
      throws XMLStreamException {
    final String type = getAttribute(xmlr, "type");
    final Integer rank = getIntegerAttribute(xmlr, "rank");
    String message = messagesPerType.get(type);
    if (message == null) {
      message = type;
    }
    final SEVERITY severity = this.toSeverity(rank);

    final List<Violation> candidates = new ArrayList<>();

    while (xmlr.hasNext()) {
      final int eventType = xmlr.next();
      if (eventType == XMLStreamConstants.START_ELEMENT) {
        if (xmlr.getLocalName().equalsIgnoreCase("SourceLine")) {
          final Optional<Integer> startLine = findIntegerAttribute(xmlr, "start");
          final Optional<Integer> endLine = findIntegerAttribute(xmlr, "end");
          if (!startLine.isPresent() || !endLine.isPresent()) {
            continue;
          }
          final String filename = getAttribute(xmlr, "sourcepath");
          final String classname = getAttribute(xmlr, "classname");
          candidates.add( //
              violationBuilder() //
                  .setParser(FINDBUGS) //
                  .setMessage(message) //
                  .setFile(filename) //
                  .setStartLine(startLine.get()) //
                  .setEndLine(endLine.get()) //
                  .setRule(type) //
                  .setSeverity(severity) //
                  .setSource(classname) //
                  .setSpecific(FINDBUGS_SPECIFIC_RANK, rank) //
                  .build() //
              );
        }
      }
      if (eventType == XMLStreamConstants.END_ELEMENT) {
        if (xmlr.getLocalName().equalsIgnoreCase("BugInstance")) {
          // End of the bug instance.
          break;
        }
      }
    }

    if (!candidates.isEmpty()) {
      /**
       * Last one is the most specific, first 2 may be class and method when the third is source
       * line.
       */
      violations.add(candidates.get(candidates.size() - 1));
    }
  }

  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();

    final Map<String, String> messagesPerType =
        this.getMessagesPerType(
            violationsLogger, this.getMessagesXml(findbugsMessagesXml, "/findbugs/messages.xml"));
    messagesPerType.putAll(
        this.getMessagesPerType(
            violationsLogger,
            this.getMessagesXml(findSecurityBugsMessagesXml, "/findbugs/fsb-messages.xml")));

    try (InputStream input = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8))) {
      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("BugInstance")) {
            this.parseBugInstance(xmlr, violations, messagesPerType);
          }
        }
      }
    }
    return violations;
  }

  private String getMessagesXml(final String staticValue, final String messagesResourceFilename)
      throws IOException {
    String messagesXml;
    if (isNullOrEmpty(staticValue)) {
      final URL resource = FindbugsParser.class.getResource(messagesResourceFilename);
      if (resource == null) {
        throw new RuntimeException("Unable to find resource " + messagesResourceFilename);
      }
      messagesXml = Utils.toString(resource);
    } else {
      messagesXml = staticValue;
    }
    return messagesXml;
  }

  /**
   * Bugs are given a rank 1-20, and grouped into the categories scariest (rank 1-4), scary (rank
   * 5-9), troubling (rank 10-14), and of concern (rank 15-20).
   */
  private SEVERITY toSeverity(final Integer rank) {
    if (rank <= 9) {
      return SEVERITY.ERROR;
    }
    if (rank <= 14) {
      return SEVERITY.WARN;
    }
    return SEVERITY.INFO;
  }
}
