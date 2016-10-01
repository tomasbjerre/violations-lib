package se.bjurr.violations.lib.parsers;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.io.Resources.getResource;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getChunks;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getContent;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getIntegerAttribute;
import static se.bjurr.violations.lib.reports.Reporter.FINDBUGS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

import com.google.common.base.Optional;
import com.google.common.io.Resources;

public class FindbugsParser implements ViolationsParser {

 /**
  * Severity rank.
  */
 public static final String FINDBUGS_SPECIFIC_RANK = "RANK";
 private static String findbugsMessagesXml;

 public static void setFindbugsMessagesXml(String findbugsMessagesXml) {
  FindbugsParser.findbugsMessagesXml = findbugsMessagesXml;
 }

 @Override
 public List<Violation> parseFile(File file) throws Exception {
  List<Violation> violations = newArrayList();
  Map<String, String> messagesPerType = getMessagesPerType();

  try (InputStream input = new FileInputStream(file)) {

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

 private Map<String, String> getMessagesPerType() {
  Map<String, String> messagesPerType = newHashMap();
  try {
   if (isNullOrEmpty(findbugsMessagesXml)) {
    findbugsMessagesXml = Resources.toString(getResource("findbugs/messages.xml"), UTF_8);
   }
   List<String> bugPatterns = getChunks(findbugsMessagesXml, "<BugPattern", "</BugPattern>");
   for (String bugPattern : bugPatterns) {
    String type = getAttribute(bugPattern, "type");
    String shortDescription = getContent(bugPattern, "ShortDescription");
    String details = getContent(bugPattern, "Details");
    messagesPerType.put(type, shortDescription + "\n\n" + details);
   }
  } catch (IOException e) {
   propagate(e);
  }
  return messagesPerType;
 }

 private void parseBugInstance(XMLStreamReader xmlr, List<Violation> violations, Map<String, String> messagesPerType)
   throws XMLStreamException {
  String type = getAttribute(xmlr, "type");
  Integer rank = getIntegerAttribute(xmlr, "rank");
  String message = messagesPerType.get(type);
  if (message == null) {
   message = type;
  }
  SEVERITY severity = toSeverity(rank);

  List<Violation> candidates = newArrayList();

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
     candidates.add(//
       violationBuilder()//
         .setReporter(FINDBUGS)//
         .setMessage(message)//
         .setFile(filename)//
         .setStartLine(startLine.get())//
         .setEndLine(endLine.get())//
         .setRule(type)//
         .setSeverity(severity)//
         .setSource(classname)//
         .setSpecific(FINDBUGS_SPECIFIC_RANK, rank)//
         .build()//
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
    * Last one is the most specific, first 2 may be class and method when the
    * third is source line.
    */
   violations.add(candidates.get(candidates.size() - 1));
  }

 }

 /**
  * Bugs are given a rank 1-20, and grouped into the categories scariest (rank
  * 1-4), scary (rank 5-9), troubling (rank 10-14), and of concern (rank 15-20).
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
