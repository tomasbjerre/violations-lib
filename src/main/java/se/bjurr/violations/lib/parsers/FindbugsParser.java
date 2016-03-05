package se.bjurr.violations.lib.parsers;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.io.Resources.getResource;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Reporter.FINDBUGS;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

import com.google.common.base.Optional;
import com.google.common.io.Files;
import com.google.common.io.Resources;

public class FindbugsParser extends ViolationsParser {

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
  String string = Files.toString(file, UTF_8);
  List<String> bugInstances = getChunks(string, "<BugInstance", "</BugInstance>");
  for (String bugInstanceChunk : bugInstances) {
   String type = getAttribute(bugInstanceChunk, "type");
   Integer rank = getIntegerAttribute(bugInstanceChunk, "rank");
   String message = messagesPerType.get(type);
   if (message == null) {
    message = type;
   }
   SEVERITY severity = toSeverity(rank);

   List<String> sourceLineChunks = getChunks(bugInstanceChunk, "<SourceLine", "/>");
   List<Violation> candidates = newArrayList();
   for (String sourceLineChunk : sourceLineChunks) {
    Optional<Integer> startLine = findIntegerAttribute(sourceLineChunk, "start");
    Optional<Integer> endLine = findIntegerAttribute(sourceLineChunk, "end");
    if (!startLine.isPresent() || !endLine.isPresent()) {
     continue;
    }
    String filename = getAttribute(sourceLineChunk, "sourcepath");
    String classname = getAttribute(sourceLineChunk, "classname");
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
   if (!candidates.isEmpty()) {
    /**
     * Last one is the most specific, first 2 may be class and method when the
     * third is source line.
     */
    violations.add(candidates.get(candidates.size() - 1));
   }
  }
  return violations;
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

}
