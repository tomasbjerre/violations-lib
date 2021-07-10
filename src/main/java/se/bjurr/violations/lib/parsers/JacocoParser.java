package se.bjurr.violations.lib.parsers;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.JACOCO;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import se.bjurr.violations.lib.ViolationsApi;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;
import se.bjurr.violations.lib.util.ViolationParserUtils;

public class JacocoParser implements ViolationsParser {

  private static final int MIN_LINE_COUNT = 4;

  private static final double MIN_COVERAGE = 70d;

  private static Element find(NodeList list, String type) {
    for (int i = 0; i < list.getLength(); i++) {
      Element item = (Element) list.item(i);
      if (item.hasAttribute("type") && item.getAttribute("type").equals(type)) {
        return item;
      }
    }
    return null;
  }

  public static void main(String[] args) {
    Set<Violation> violations = ViolationsApi.violationsApi() //
        .withPattern(".*/x\\.xml$") //
        .inFolder(".") //
        .findAll(Parser.JACOCO) //
        .violations();
    for (Violation violation : violations) {
      System.out.println("violation = " + violation);
    }
  }

  @Override
  public Set<Violation> parseReportOutput(String reportContent, ViolationsLogger violationsLogger)
      throws Exception {
    Set<Violation> violations = new LinkedHashSet<>();
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    documentBuilderFactory
        .setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
    documentBuilderFactory
        .setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
    InputSource is = new InputSource(new StringReader(reportContent));
    Document document = db.parse(is);
    document.getDocumentElement().normalize();
    NodeList list = document.getElementsByTagName("package");
    for (int temp = 0; temp < list.getLength(); temp++) {
      violations.addAll(process((Element) list.item(temp)));
    }
    return violations;
  }

  private Set<Violation> process(Element p) {
    Set<Violation> violations = new LinkedHashSet<>();
    NodeList list = p.getElementsByTagName("class");
    for (int temp = 0; temp < list.getLength(); temp++) {
      violations.addAll(process(p, (Element) list.item(temp)));
    }
    return violations;
  }

  private Set<Violation> process(Element p, Element c) {
    Set<Violation> violations = new LinkedHashSet<>();
    NodeList list = c.getElementsByTagName("method");
    for (int temp = 0; temp < list.getLength(); temp++) {
      process(p, c, (Element) list.item(temp)).ifPresent(violations::add);
    }
    return violations;
  }

  private Optional<Violation> process(Element p, Element c, Element m) {
    CoverageDetails cl = new CoverageDetails(
        find(m.getElementsByTagName("counter"), "LINE"));
    if (cl.getTotal() < MIN_LINE_COUNT) {
      return Optional.empty();
    }
    CoverageDetails ci = new CoverageDetails(
        find(m.getElementsByTagName("counter"), "INSTRUCTION"));
    if (ci.getCoverage() >= MIN_COVERAGE) {
      return Optional.empty();
    }
    return Optional.of(violationBuilder()
        .setParser(JACOCO)
        .setStartLine(Integer.valueOf(m.getAttribute("line")))
        .setColumn(1)
        .setFile(p.getAttribute("name") + "/" + c.getAttribute("sourcefilename"))
        .setSeverity(SEVERITY.WARN)
        .setMessage(format("Covered %d out of %d instructions (%.2f%%) for %s%s",
            ci.getCovered(),
            ci.getTotal(),
            ci.getCoverage(),
            m.getAttribute("name"),
            m.getAttribute("desc")))
        .build());
  }

  private static final class CoverageDetails {

    private final Element element;

    public CoverageDetails(Element element) {
      this.element = element;
    }

    int getCovered() {
      return element != null ? Integer.parseInt(element.getAttribute("covered")) : 0;
    }

    int getMissed() {
      return element != null ? Integer.parseInt(element.getAttribute("missed")) : 0;
    }

    int getTotal() {
      return getCovered() + getMissed();
    }

    double getCoverage() {
      return getTotal() > 0 ? getCovered() * 100d / getTotal() : 1;
    }
  }
}
