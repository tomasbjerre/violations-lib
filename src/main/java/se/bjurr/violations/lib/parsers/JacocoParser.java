package se.bjurr.violations.lib.parsers;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.JACOCO;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class JacocoParser implements ViolationsParser {

  private final int minLineCount;

  private final double minCoverage;

  public JacocoParser() {
    this(4, 0.70);
  }

  public JacocoParser(int minLineCount, double minCoverage) {
    this.minLineCount = minLineCount;
    this.minCoverage = minCoverage;
  }

  @Override
  public Set<Violation> parseReportOutput(String reportContent, ViolationsLogger violationsLogger)
      throws Exception {
    Set<Violation> violations = new LinkedHashSet<>();
    try (InputStream input = new ByteArrayInputStream(reportContent.getBytes(UTF_8))) {
      MethodViolationBuilder builder = new MethodViolationBuilder();
      XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);
      while (xmlr.hasNext()) {
        final int eventType = xmlr.next();
        switch (eventType) {
          case XMLStreamConstants.START_ELEMENT:
            switch (xmlr.getLocalName()) {
              case "package":
                builder.setPackageDetails(getAttribute(xmlr, "name"));
                break;
              case "class":
                builder.setClassDetails(getAttribute(xmlr, "sourcefilename"));
                break;
              case "method":
                builder.setMethodDetails(
                    getAttribute(xmlr, "name"),
                    getAttribute(xmlr, "desc"),
                    Integer.parseInt(getAttribute(xmlr, "line")));
                break;
              case "counter":
                builder.setCounterDetails(
                    getAttribute(xmlr, "type"),
                    Integer.parseInt(getAttribute(xmlr, "covered")),
                    Integer.parseInt(getAttribute(xmlr, "missed")));
                break;
            }
            break;
          case XMLStreamConstants.END_ELEMENT:
            if (xmlr.getLocalName().equals("method")) {
              builder.build(minLineCount, minCoverage).ifPresent(violations::add);
            }
            break;
        }
      }
    }
    return violations;
  }

  private static class MethodViolationBuilder {

    private String packageName;

    private String fileName;

    private String methodName;

    private String methodDescription;

    private int methodLine;

    private Map<String, CoverageDetails> coverage = new HashMap<>();

    public void setPackageDetails(String packageName) {
      this.packageName = packageName;
    }

    public void setClassDetails(String fileName) {
      this.fileName = fileName;
    }

    public void setMethodDetails(String methodName, String methodDescription, int methodLine) {
      this.methodName = methodName;
      this.methodDescription = methodDescription;
      this.methodLine = methodLine;
      this.coverage.clear();
    }

    public void setCounterDetails(String counterName, int covered, int missed) {
      this.coverage.put(counterName, new CoverageDetails(covered, missed));
    }

    public Optional<Violation> build(int minLineCount, double minCoverage) {
      CoverageDetails cl = coverage.get("LINE");
      if (cl.getTotal() < minLineCount) {
        return Optional.empty();
      }
      CoverageDetails ci = coverage.get("INSTRUCTION");
      if (ci.getCoverage() >= minCoverage) {
        return Optional.empty();
      }
      return Optional.of(violationBuilder()
          .setParser(JACOCO)
          .setStartLine(methodLine)
          .setColumn(1)
          .setFile(packageName + "/" + fileName)
          .setSeverity(SEVERITY.WARN)
          .setMessage(format("Covered %d out of %d instructions (%.2f%%) for %s%s",
              ci.getCovered(),
              ci.getTotal(),
              ci.getCoverage() * 100d,
              methodName,
              methodDescription))
          .build());
    }
  }

  private static final class CoverageDetails {

    private final int covered;

    private final int missed;

    public CoverageDetails(int covered, int missed) {
      this.covered = covered;
      this.missed = missed;
    }

    public int getCovered() {
      return covered;
    }

    public int getMissed() {
      return missed;
    }

    public int getTotal() {
      return getCovered() + getMissed();
    }

    public double getCoverage() {
      return getTotal() > 0 ? getCovered() * 1d / getTotal() : 1d;
    }
  }
}
