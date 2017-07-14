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
    Map<String, String> messagesPerType = new HashMap<>();
    try {
      if (isNullOrEmpty(findbugsMessagesXml)) {
        String messagesResourceFilename = "/findbugs/messages.xml";
        URL resource = FindbugsParser.class.getResource(messagesResourceFilename);
        if (resource == null) {
          throw new RuntimeException("Unable to find resource " + messagesResourceFilename);
        }
        findbugsMessagesXml = Utils.toString(resource);
      }
      List<String> bugPatterns = getChunks(findbugsMessagesXml, "<BugPattern", "</BugPattern>");
      for (String bugPattern : bugPatterns) {
        String type = getAttribute(bugPattern, "type");
        String shortDescription = getContent(bugPattern, "ShortDescription");
        String details = getContent(bugPattern, "Details");
        messagesPerType.put(type, shortDescription + "\n\n" + details);
      }
    } catch (IOException e) {
      LOG.log(SEVERE, e.getMessage(), e);
    }
    return messagesPerType;
  }

  private void parseBugInstance(
      XMLStreamReader xmlr, List<Violation> violations, Map<String, String> messagesPerType)
      throws XMLStreamException {
    String type = getAttribute(xmlr, "type");
    Integer rank = getIntegerAttribute(xmlr, "rank");
    String message = messagesPerType.get(type);
    if (message == null) {
      message = type;
    }
    SEVERITY severity = toSeverity(rank);

    List<Violation> candidates = new ArrayList<>();

    while (xmlr.hasNext()) {
      int eventType = xmlr.next();
      if (eventType == XMLStreamConstants.START_ELEMENT) {
        if (xmlr.getLocalName().equals("SourceLine")) {
          Optional<Integer> startLine = findIntegerAttribute(xmlr, "start");
          Optional<Integer> endLine = findIntegerAttribute(xmlr, "end");
          if (!startLine.isPresent() || !endLine.isPresent()) {
            continue;
          }
          String filename = getAttribute(xmlr, "sourcepath");
          String classname = getAttribute(xmlr, "classname");
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
        if (xmlr.getLocalName().equals("BugInstance")) {
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
    List<Violation> violations = new ArrayList<>();
    Map<String, String> messagesPerType = getMessagesPerType();

    try (InputStream input = new ByteArrayInputStream(string.getBytes())) {

      XMLInputFactory factory = XMLInputFactory.newInstance();
      XMLStreamReader xmlr = factory.createXMLStreamReader(input);

      while (xmlr.hasNext()) {
        int eventType = xmlr.next();
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          if (xmlr.getLocalName().equals("BugInstance")) {
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
