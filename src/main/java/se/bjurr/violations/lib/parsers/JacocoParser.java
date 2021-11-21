package se.bjurr.violations.lib.parsers;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.JACOCO;
import static se.bjurr.violations.lib.util.ViolationParserUtils.findIntegerAttribute;
import static se.bjurr.violations.lib.util.ViolationParserUtils.getAttribute;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class JacocoParser implements ViolationsParser {

  private final JacocoParserSettings settings;

  public JacocoParser() {
    this.settings = new JacocoParserSettings();
  }

  public JacocoParser(final JacocoParserSettings settings) {
    this.settings = settings;
  }

  @Override
  @SuppressFBWarnings("SF_SWITCH_NO_DEFAULT")
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    try (InputStream input = new ByteArrayInputStream(reportContent.getBytes(UTF_8))) {
      final MethodViolationBuilder builder = new MethodViolationBuilder();
      final XMLStreamReader xmlr = ViolationParserUtils.createXmlReader(input);
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
                    findIntegerAttribute(xmlr, "line").orElse(0));
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
              builder
                  .build(this.settings.getMinLineCount(), this.settings.getMinCoverage())
                  .ifPresent(violations::add);
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

    private final Map<String, CoverageDetails> coverage = new TreeMap<>();

    public void setPackageDetails(final String packageName) {
      this.packageName = packageName;
    }

    public void setClassDetails(final String fileName) {
      this.fileName = fileName;
    }

    public void setMethodDetails(
        final String methodName, final String methodDescription, final int methodLine) {
      this.methodName = methodName;
      this.methodDescription = methodDescription;
      this.methodLine = methodLine;
      this.coverage.clear();
    }

    public void setCounterDetails(final String counterName, final int covered, final int missed) {
      this.coverage.put(counterName, new CoverageDetails(covered, missed));
    }

    public Optional<Violation> build(final int minLineCount, final double minCoverage) {
      if (this.methodLine == 0) {
        return Optional.empty();
      }
      final CoverageDetails cl = this.coverage.get("LINE");
      if (cl.getTotal() < minLineCount) {
        return Optional.empty();
      }
      final CoverageDetails ci = this.coverage.get("INSTRUCTION");
      if (ci.getCoverage() >= minCoverage) {
        return Optional.empty();
      }
      return Optional.of(
          violationBuilder()
              .setParser(JACOCO)
              .setStartLine(this.methodLine)
              .setColumn(1)
              .setFile(this.packageName + "/" + this.fileName)
              .setSeverity(SEVERITY.WARN)
              .setMessage(
                  format(
                      "Covered %d out of %d instructions (%.2f%%) for %s%s",
                      ci.getCovered(),
                      ci.getTotal(),
                      ci.getCoverage() * 100d,
                      this.methodName,
                      this.methodDescription))
              .build());
    }
  }

  private static final class CoverageDetails {

    private final int covered;

    private final int missed;

    public CoverageDetails(final int covered, final int missed) {
      this.covered = covered;
      this.missed = missed;
    }

    public int getCovered() {
      return this.covered;
    }

    public int getMissed() {
      return this.missed;
    }

    public int getTotal() {
      return this.getCovered() + this.getMissed();
    }

    public double getCoverage() {
      return this.getTotal() > 0 ? this.getCovered() * 1d / this.getTotal() : 1d;
    }
  }
}
