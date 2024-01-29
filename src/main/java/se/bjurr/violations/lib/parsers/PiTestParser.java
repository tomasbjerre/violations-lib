package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.PITEST;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class PiTestParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String content, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();

    try (InputStream input = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))) {
      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);
      String status = null;
      String detected = null;
      String mutatedClass = null;
      String sourceFile = null;
      String mutatedMethod = null;
      String methodDescription = null;
      String mutator = null;
      int index = 0;
      int startLine = 0;
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("mutation")) {
            mutatedClass = null;
            sourceFile = null;
            mutatedMethod = null;
            methodDescription = null;
            mutator = null;
            index = 0;
            startLine = 0;
            status = getAttribute(xmlr, "status");
            detected = getAttribute(xmlr, "detected");
          }
          if (xmlr.getLocalName().equalsIgnoreCase("mutatedClass")) {
            mutatedClass = xmlr.getElementText();
          }
          if (xmlr.getLocalName().equalsIgnoreCase("sourceFile")) {
            sourceFile = xmlr.getElementText();
          }
          if (xmlr.getLocalName().equalsIgnoreCase("mutatedMethod")) {
            mutatedMethod = xmlr.getElementText();
          }
          if (xmlr.getLocalName().equalsIgnoreCase("methodDescription")) {
            methodDescription = xmlr.getElementText();
          }
          if (xmlr.getLocalName().equalsIgnoreCase("mutator")) {
            mutator = xmlr.getElementText();
          }
          if (xmlr.getLocalName().equalsIgnoreCase("lineNumber")) {
            startLine = Integer.parseInt(xmlr.getElementText());
          }
          if (xmlr.getLocalName().equalsIgnoreCase("index")) {
            index = Integer.parseInt(xmlr.getElementText());
          }
        }
        if (eventType == XMLStreamConstants.END_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("mutation")) {
            final Map<String, String> specifics = new HashMap<>();
            specifics.put("detected", detected);
            specifics.put("mutatedMethod", mutatedMethod);
            specifics.put("mutatedClass", mutatedClass);
            specifics.put("status", status);
            specifics.put("methodDescription", methodDescription);
            final String filename = this.toFilename(mutatedClass, sourceFile);
            final String message = status + ", " + mutator + ", " + methodDescription;
            final Violation violation =
                violationBuilder() //
                    .setRule(mutator) //
                    .setSource(sourceFile) //
                    .setParser(PITEST) //
                    .setStartLine(startLine) //
                    .setColumn(index) //
                    .setFile(filename) //
                    .setSeverity(WARN) //
                    .setMessage(message) //
                    .setSpecifics(specifics) //
                    .build();
            violations.add(violation);
          }
        }
      }
    }
    return violations;
  }

  /** Use package from mutadedClass and assume sourceFile is in that package. */
  private String toFilename(final String mutatedClass, final String sourceFile) {
    return mutatedClass.substring(0, mutatedClass.lastIndexOf(".")).replaceAll("\\.", "/")
        + "/"
        + sourceFile;
  }
}
