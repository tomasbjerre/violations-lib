package se.bjurr.violations.lib.parsers;

import static java.nio.charset.StandardCharsets.UTF_8;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.JUNIT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class JUnitParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    try (InputStream input = new ByteArrayInputStream(reportContent.getBytes(UTF_8))) {

      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);
      String className = null;
      String name = null;
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("testcase")) {
            className = getAttribute(xmlr, "classname");
            name = getAttribute(xmlr, "name");
          } else if (xmlr.getLocalName().equalsIgnoreCase("failure")
              || xmlr.getLocalName().equalsIgnoreCase("error")) {
            final Violation v = this.parseFailure(xmlr, className, name, violationsLogger);
            if (v != null) {
              violations.add(v);
            }
          }
        }
      }
    }

    return violations;
  }

  private static class FileAndLine {
    public String file;
    public Integer line;
  }

  private Violation parseFailure(
      final XMLStreamReader xmlr,
      final String className,
      final String name,
      final ViolationsLogger violationsLogger)
      throws Exception {
    final String messageAttr = xmlr.getAttributeValue("", "message");
    final String failureContent = xmlr.getElementText();
    FileAndLine fl = this.findFilePathInContent(failureContent, className);
    if (fl == null) {
      fl = this.deriveFileFromClass(failureContent);
    }
    if (fl == null) {
      violationsLogger.log(Level.FINE, "Cannot determine file and line in:\n" + failureContent);
      return null;
    }
    final String message =
        name + " : " + (messageAttr != null ? messageAttr + " " + failureContent : failureContent);
    return violationBuilder() //
        .setParser(JUNIT) //
        .setMessage(message.trim()) //
        .setStartLine(fl.line) //
        .setFile(fl.file) //
        .setSource(className) //
        .setSeverity(ERROR) //
        .build();
  }

  private FileAndLine deriveFileFromClass(final String failureContent) {
    final String failureContentFrontSlash = failureContent.replace("\\", "/");
    final Matcher matcher =
        Pattern.compile("((([a-zA-Z]+?/)|([a-zA-Z]:/)|(/))([^:]+?)):(\\d+)")
            .matcher(failureContentFrontSlash);
    final boolean foundFilePath = matcher.find();
    if (foundFilePath) {
      final FileAndLine fl = new FileAndLine();
      fl.file = matcher.group(1);
      fl.line = Integer.parseInt(matcher.group(7));
      return fl;
    }
    return null;
  }

  private FileAndLine findFilePathInContent(final String failureContent, final String className) {
    final String failureContentFrontSlash = failureContent.replace("\\", "/");
    final Matcher matcher =
        Pattern.compile("\\s+?at\\s([a-zA-Z0-9\\.]*)\\(([^:]+):(\\d+?)\\)", Pattern.MULTILINE)
            .matcher(failureContentFrontSlash);
    final List<FileAndLine> found = new ArrayList<>();
    while (matcher.find()) {
      final FileAndLine fl = new FileAndLine();
      final String classNameFromMatcher = matcher.group(1);
      String filepath = classNameFromMatcher.replace(".", "/");
      filepath = filepath.substring(0, filepath.lastIndexOf("/"));
      if (filepath.lastIndexOf("/") == -1) {
        filepath = "";
      } else {
        filepath = filepath.substring(0, filepath.lastIndexOf("/"));
      }
      final String filename = matcher.group(2);
      if (filepath.isEmpty()) {
        fl.file = filename;
      } else {
        fl.file = filepath + "/" + filename;
      }
      fl.line = Integer.parseInt(matcher.group(3));
      found.add(fl);
    }
    if (found.size() == 0) {
      return null;
    }
    if (found.size() == 1) {
      return found.get(0);
    }
    for (final FileAndLine candidate : found) {
      if (candidate.file.startsWith(className.replace(".", "/"))) {
        return candidate;
      }
    }
    return found.get(0);
  }
}
