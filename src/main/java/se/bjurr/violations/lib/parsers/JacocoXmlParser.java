package se.bjurr.violations.lib.parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import se.bjurr.violations.lib.ViolationsApi;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.JACOCO;

public class JacocoXmlParser implements ViolationsParser {

    private static final int MIN_LINE_COUNT = 4;

    private static final double MIN_COVERAGE = 0.7;

    @Override
    public Set<Violation> parseReportOutput(String reportContent, ViolationsLogger violationsLogger) throws Exception {
        Set<Violation> violations = new HashSet<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
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
        Set<Violation> violations = new TreeSet<>();
        NodeList list = p.getElementsByTagName("class");
        for (int temp = 0; temp < list.getLength(); temp++) {
            violations.addAll(process(p, (Element) list.item(temp)));
        }
        return violations;
    }

    private Set<Violation> process(Element p, Element c) {
        Set<Violation> violations = new TreeSet<>();
        NodeList list = c.getElementsByTagName("method");
        for (int temp = 0; temp < list.getLength(); temp++) {
            process(p, c, (Element) list.item(temp)).ifPresent(violations::add);
        }
        return violations;
    }

    private Optional<Violation> process(Element p, Element c, Element m) {
        CoverageDetails cl = new CoverageDetails(find(m.getElementsByTagName("counter"), "type", "LINE"));
        if (cl.getTotal() < MIN_LINE_COUNT) {
            System.out.println("cl = " + cl);
            return Optional.empty();
        }
        CoverageDetails ci = new CoverageDetails(find(m.getElementsByTagName("counter"), "type", "INSTRUCTION"));
        if (ci.getCoverage() >= MIN_COVERAGE) {
            System.out.println("ci = " + ci);
            return Optional.empty();
        }
        return Optional.of(violationBuilder()
                .setParser(JACOCO)
                .setStartLine(Integer.valueOf(m.getAttribute("line")))
                .setColumn(1)
                .setFile(new File(p.getAttribute("name"), c.getAttribute("sourceFileName")).toPath().toString())
                .setSeverity(SEVERITY.WARN) //
                .setMessage(String.format("Covered %d out of %d instructions (%.2f%%) for %s%s",
                        ci.getCovered(),
                        ci.getTotal(),
                        ci.getCoverage(),
                        m.getAttribute("name"),
                        m.getAttribute("desc")))
                .build());
    }

    private static Element find(NodeList list, String attribute, String value) {
        for (int i = 0; i < list.getLength(); i++) {
            Element item = (Element) list.item(i);
            if (item.hasAttribute(attribute) && item.getAttribute(attribute).equals(value)) ;
            {
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

    private static final class CoverageDetails {

        private final Element element;

        public CoverageDetails(Element element) {
            this.element = element;
        }

        int getCovered() {
            return element != null ? Integer.valueOf(element.getAttribute("covered")) : 0;
        }

        int getMissed() {
            return element != null ? Integer.valueOf(element.getAttribute("missed")) : 0;
        }

        int getTotal() {
            return getCovered() + getMissed();
        }

        double getCoverage() {
            return getTotal() > 0 ? getCovered() / getTotal() * 100 : 1;
        }
    }
}
