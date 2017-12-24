package se.bjurr.violations.lib.parsers;

import static java.util.logging.Level.SEVERE;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getContent;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerAttribute;
import static se.bjurr.violations.lib.reports.Parser.FINDBUGS;
import static se.bjurr.violations.lib.util.Utils.isNullOrEmpty;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.Optional;
import se.bjurr.violations.lib.util.Utils;

public class FindbugsParser implements ViolationsParser {
  private static Logger LOG = Logger.getLogger(FindbugsParser.class.getSimpleName());
  /** Severity rank. */
  public static final String FINDBUGS_SPECIFIC_RANK = "RANK";

  private static String findbugsMessagesXml;

  public static void setFindbugsMessagesXml(String findbugsMessagesXml) {
    FindbugsParser.findbugsMessagesXml = findbugsMessagesXml;
  }

  private Map<String, String> getMessagesPerType() {
    final Map<String, String> messagesPerType = new HashMap<>();
    try {
      if (isNullOrEmpty(findbugsMessagesXml)) {
        final String messagesResourceFilename = "/findbugs/messages.xml";
        final URL resource = FindbugsParser.class.getResource(messagesResourceFilename);
        if (resource == null) {
          throw new RuntimeException("Unable to find resource " + messagesResourceFilename);
        }
        findbugsMessagesXml = Utils.toString(resource);
      }
      final List<String> bugPatterns =
          getChunks(findbugsMessagesXml, "<BugPattern", "</BugPattern>");
      for (final String bugPattern : bugPatterns) {
        final String type = getAttribute(bugPattern, "type");
        final String shortDescription = getContent(bugPattern, "ShortDescription");
        final String details = getContent(bugPattern, "Details");
        messagesPerType.put(type, shortDescription + "\n\n" + details);
      }
    } catch (final IOException e) {
      LOG.log(SEVERE, e.getMessage(), e);
    }
    return messagesPerType;
  }

  private void parseBugInstance(
      XMLStreamReader xmlr, List<Violation> violations, Map<String, String> messagesPerType)
      throws XMLStreamException {
    final String type = getAttribute(xmlr, "type");
    final Integer rank = getIntegerAttribute(xmlr, "rank");
    String message = messagesPerType.get(type);
    if (message == null) {
      message = type;
    }
    final SEVERITY severity = toSeverity(rank);

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
  public List<Violation> parseReportOutput(String string) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    final Map<String, String> messagesPerType = getMessagesPerType();

    try (InputStream input = new ByteArrayInputStream(string.getBytes("UTF-8"))) {

      final XMLInputFactory factory = XMLInputFactory.newInstance();
      final XMLStreamReader xmlr = factory.createXMLStreamReader(input);

      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("BugInstance")) {
            parseBugInstance(xmlr, violations, messagesPerType);
          }
        }
      }
    }
    return violations;
  }

  /**
   * Bugs are given a rank 1-20, and grouped into the categories scariest (rank 1-4), scary (rank
   * 5-9), troubling (rank 10-14), and of concern (rank 15-20).
   */
  private SEVERITY toSeverity(Integer rank) {
    if (rank <= 9) {
      return SEVERITY.ERROR;
    }
    if (rank <= 14) {
      return SEVERITY.WARN;
    }
    return SEVERITY.INFO;
  }
}
