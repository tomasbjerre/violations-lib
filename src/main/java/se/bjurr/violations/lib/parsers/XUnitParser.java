package se.bjurr.violations.lib.parsers;

import static java.nio.charset.StandardCharsets.UTF_8;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.XUNIT;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class XUnitParser implements ViolationsParser {

  @Override
  public List<Violation> parseReportOutput(String string) throws Exception {
    final List<Violation> violations = new ArrayList<>();
    try (InputStream input = new ByteArrayInputStream(string.getBytes(UTF_8))) {
      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);
      Map<String, String> specifics = new HashMap<>();
      String testSuiteName = null;
      String file = null;
      String message = null;
      String type = null;
      String prevLocalName = "";
      String testcaseName = "";
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("testsuite")) {
            prevLocalName = xmlr.getLocalName();
            Integer failures = findIntegerAttribute(xmlr, "failures").orElse(-1);
            Integer errors = findIntegerAttribute(xmlr, "errors").orElse(-1);
            Integer tests = findIntegerAttribute(xmlr, "tests").orElse(-1);
            testSuiteName = findAttribute(xmlr, "name").orElse("");
            file = findAttribute(xmlr, "file").orElse("");
            specifics.put("tests", tests.toString());
            specifics.put("errors", errors.toString());
            specifics.put("failures", failures.toString());
          }
        }

        if (eventType == XMLStreamConstants.START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("testcase")) {
            prevLocalName = xmlr.getLocalName();
            testcaseName = findAttribute(xmlr, "name").orElse("");
            specifics.put("testcaseName", testcaseName);
          }
        }
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          if (xmlr.getLocalName().equalsIgnoreCase("failure")) {
            prevLocalName = xmlr.getLocalName();
            message = findAttribute(xmlr, "message").orElse("");
            type = findAttribute(xmlr, "type").orElse("");
          }
        }

        if (prevLocalName.equalsIgnoreCase("failure")
            && eventType == XMLStreamConstants.CHARACTERS) {
          prevLocalName = "";
          String content = xmlr.getText();
          String violationFile = testSuiteName;
          if (!file.isEmpty()) {
            violationFile = file;
          }
          violations.add( //
              violationBuilder() //
                  .setFile(violationFile) //
                  .setMessage(message + "\n\n" + content) //
                  .setParser(XUNIT) //
                  .setCategory(type) //
                  .setSeverity(ERROR) //
                  .setSpecifics(specifics) //
                  .setStartLine(0) //
                  .setRule(testcaseName) //
                  .build() //
              );
        }
      }
    }
    return violations;
  }
}
