package se.bjurr.violations.lib.util;

import static java.lang.Integer.parseInt;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.regex.Pattern.quote;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;

public final class ViolationParserUtils {

  private ViolationParserUtils() {}

  public static String asString(final XMLStreamReader xmlr) throws Exception {
    final Transformer transformer = createTranformer();
    final StringWriter stringWriter = new StringWriter();
    transformer.transform(new StAXSource(xmlr), new StreamResult(stringWriter));
    return stringWriter.toString();
  }

  public static Optional<String> findAttribute(final XMLStreamReader in, final String attribute) {
    return ofNullable(in.getAttributeValue("", attribute));
  }

  public static Optional<Integer> findIntegerAttribute(
      final XMLStreamReader in, final String attribute) {
    final String attr = in.getAttributeValue("", attribute);
    if (attr == null) {
      return empty();
    } else {
      return ofNullable(Integer.parseInt(attr));
    }
  }

  public static String getAttribute(final XMLStreamReader in, final String attribute) {
    final String foundOpt = in.getAttributeValue("", attribute);
    if (foundOpt == null) {
      String foundin;
      try {
        foundin = asString(in);
      } catch (final Exception e) {
        throw new RuntimeException(e);
      }
      throw new RuntimeException("\"" + attribute + "\" not found in:\n" + foundin);
    }
    return foundOpt;
  }

  public static Integer getIntegerAttribute(final XMLStreamReader in, final String attribute) {
    return parseInt(getAttribute(in, attribute));
  }

  public static List<String> getLines(final String string) {
    return Arrays.asList(string.split("\n"));
  }

  /** @return List per line in String, with groups from regexpPerLine. */
  public static List<List<String>> getLines(final String string, final String regexpPerLine) {
    final List<List<String>> results = new ArrayList<>();
    final Pattern pattern = Pattern.compile(regexpPerLine);
    for (final String line : string.split("\n")) {
      final List<String> found = getLineParts(pattern, line);
      if (found != null) {
        results.add(found);
      }
    }
    return results;
  }

  public static List<String> getLineParts(final Pattern pattern, final String line) {
    final Matcher matcher = pattern.matcher(line);
    if (!matcher.find()) {
      return null;
    }
    final List<String> lineParts = new ArrayList<>();
    for (int g = 0; g <= matcher.groupCount(); g++) {
      lineParts.add(matcher.group(g));
    }
    return lineParts;
  }

  /**
   * Match one regexp at a time. Remove the matched part from the string, trim, and match next
   * regexp on that string...
   */
  public static List<String> getParts(String string, final String... regexpList) {
    final List<String> parts = new ArrayList<>();
    for (final String regexp : regexpList) {
      final Pattern pattern = Pattern.compile(regexp);
      final Matcher matcher = pattern.matcher(string);
      final boolean found = matcher.find();
      if (!found) {
        return new ArrayList<>();
      }
      final String part = matcher.group(1).trim();
      parts.add(part);
      string = string.replaceFirst(quote(matcher.group()), "").trim();
    }
    return parts;
  }

  private static Transformer createTranformer()
      throws TransformerFactoryConfigurationError, TransformerConfigurationException {
    final TransformerFactory factory = TransformerFactory.newInstance();
    factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
    final Transformer transformer = factory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    return transformer;
  }

  public static XMLStreamReader createXmlReader(final InputStream input) throws XMLStreamException {
    final XMLInputFactory factory = XMLInputFactory.newInstance();
    factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
    final XMLStreamReader xmlr = factory.createXMLStreamReader(input);
    return xmlr;
  }
}
