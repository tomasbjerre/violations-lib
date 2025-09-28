package se.bjurr.violations.lib.parsers;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CPD;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getIntegerAttribute;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationParserUtils;

/**
 * Parser for CPD (Copy Paste Detector) reports. Supports both: 1. Old CPD format: Simple XML
 * without namespace 2. New PMD CPD format: XML with PMD namespace and additional attributes
 */
public class CPDParser implements ViolationsParser {

  private SEVERITY getSeverityFromTokens(final Integer tokens) {
    if (tokens == null) {
      return INFO;
    }
    if (tokens < 100) {
      return INFO;
    }
    if (tokens < 1000) {
      return WARN;
    }
    return ERROR;
  }

  private SEVERITY getSeverityFromLines(final Integer lines) {
    if (lines == null) {
      return INFO;
    }
    if (lines < 10) {
      return INFO;
    }
    if (lines < 50) {
      return WARN;
    }
    return ERROR;
  }

  @Override
  public Set<Violation> parseReportOutput(
      final String string, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    try (InputStream input = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8))) {

      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);

      // Check if it's new PMD CPD format or old format
      boolean isNewPmdCpdFormat =
          string.contains("xmlns=\"https://pmd-code.org/schema/cpd-report\"");

      if (isNewPmdCpdFormat) {
        parseNewPmdCpdFormat(xmlr, violations);
      } else {
        parseOldCpdFormat(xmlr, violations);
      }
    }
    return violations;
  }

  private void parseNewPmdCpdFormat(final XMLStreamReader xmlr, final Set<Violation> violations)
      throws Exception {
    final List<FileInfo> files = new ArrayList<>();
    Integer lines = null;
    Integer tokens = null;

    while (xmlr.hasNext()) {
      final int eventType = xmlr.next();
      if (eventType == START_ELEMENT) {
        if (xmlr.getLocalName().equalsIgnoreCase("duplication")) {
          lines = getIntegerAttribute(xmlr, "lines");
          tokens = getIntegerAttribute(xmlr, "tokens");
        }
        if (xmlr.getLocalName().equalsIgnoreCase("file")) {
          final String path = getAttribute(xmlr, "path");
          final Integer startLine = getIntegerAttribute(xmlr, "line");
          final Integer endLine = getIntegerAttribute(xmlr, "endline");
          final Integer column = getIntegerAttribute(xmlr, "column");
          final Integer endColumn = getIntegerAttribute(xmlr, "endcolumn");

          files.add(new FileInfo(path, startLine, endLine, column, endColumn));
        }
        if (xmlr.getLocalName().equalsIgnoreCase("codefragment")) {
          String codeFragment = xmlr.getElementText().trim();

          for (final FileInfo fileInfo : files) {
            final String message =
                createNewFormatMessage(lines, tokens, files.size(), codeFragment);

            final Violation violation =
                violationBuilder()
                    .setParser(CPD)
                    .setFile(fileInfo.path)
                    .setMessage(message)
                    .setRule("Code Duplication")
                    .setSeverity(this.getSeverityFromLines(lines))
                    .setStartLine(fileInfo.startLine)
                    .setEndLine(fileInfo.endLine)
                    .setColumn(fileInfo.column)
                    .setEndColumn(fileInfo.endColumn)
                    .build();
            violations.add(violation);
          }

          files.clear();
        }
      }
    }
  }

  private void parseOldCpdFormat(final XMLStreamReader xmlr, final Set<Violation> violations)
      throws Exception {
    final List<String> files = new ArrayList<>();
    final List<Integer> filesLine = new ArrayList<>();
    Integer tokens = null;

    while (xmlr.hasNext()) {
      final int eventType = xmlr.next();
      if (eventType == START_ELEMENT) {
        if (xmlr.getLocalName().equalsIgnoreCase("duplication")) {
          tokens = getIntegerAttribute(xmlr, "tokens");
        }
        if (xmlr.getLocalName().equalsIgnoreCase("file")) {
          files.add(getAttribute(xmlr, "path"));
          filesLine.add(getIntegerAttribute(xmlr, "line"));
        }
        if (xmlr.getLocalName().equalsIgnoreCase("codefragment")) {
          final String codeFragment = xmlr.getElementText().trim();
          for (int i = 0; i < filesLine.size(); i++) {
            final String file = files.get(i);
            final Integer line = filesLine.get(i);
            final Violation violation =
                violationBuilder()
                    .setParser(CPD)
                    .setFile(file)
                    .setMessage(codeFragment)
                    .setRule("DUPLICATION")
                    .setSeverity(this.getSeverityFromTokens(tokens))
                    .setStartLine(line)
                    .build();
            violations.add(violation);
          }
          files.clear();
          filesLine.clear();
        }
      }
    }
  }

  private String createNewFormatMessage(
      final Integer lines, final Integer tokens, final int fileCount, final String codeFragment) {
    final StringBuilder message = new StringBuilder();
    message.append("Duplicated code detected");

    if (lines != null) {
      message.append(" (").append(lines).append(" lines");
    }
    if (tokens != null) {
      if (lines != null) {
        message.append(", ");
      } else {
        message.append(" (");
      }
      message.append(tokens).append(" tokens");
    }
    if (lines != null || tokens != null) {
      message.append(")");
    }

    message.append(" found in ").append(fileCount).append(" files");

    if (codeFragment != null && !codeFragment.isEmpty()) {
      // Limit the code fragment size for readability
      String fragment = codeFragment;
      if (fragment.length() > 200) {
        fragment = fragment.substring(0, 200) + "...";
      }
      message.append(":\n").append(fragment);
    }

    return message.toString();
  }

  private static class FileInfo {
    final String path;
    final Integer startLine;
    final Integer endLine;
    final Integer column;
    final Integer endColumn;

    FileInfo(
        final String path,
        final Integer startLine,
        final Integer endLine,
        final Integer column,
        final Integer endColumn) {
      this.path = path;
      this.startLine = startLine;
      this.endLine = endLine;
      this.column = column;
      this.endColumn = endColumn;
    }
  }
}
