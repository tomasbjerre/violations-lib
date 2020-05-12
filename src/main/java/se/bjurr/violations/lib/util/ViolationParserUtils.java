package se.bjurr.violations.lib.util;

import static java.lang.Integer.parseInt;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.quote;
import static se.bjurr.violations.lib.util.StringUtils.xmlDecode;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;

public final class ViolationParserUtils {

  public static String asString(final XMLStreamReader xmlr) throws Exception {
    final Transformer transformer = TransformerFactory.newInstance().newTransformer();
    final StringWriter stringWriter = new StringWriter();
    transformer.transform(new StAXSource(xmlr), new StreamResult(stringWriter));
    return stringWriter.toString();
  }

  public static Optional<String> findAttribute(final String in, final String attribute) {
    Pattern pattern = Pattern.compile("[\\s<^]"+attribute + "='([^']+?)'");
    Matcher matcher = pattern.matcher(in);
    if (matcher.find()) {
      return ofNullable(xmlDecode(matcher.group(1)));
    }
    pattern = Pattern.compile("[\\s<^]"+attribute + "=\"([^\"]+?)\"");
    matcher = pattern.matcher(in);
    if (matcher.find()) {
      return ofNullable(xmlDecode(matcher.group(1)));
    }
    return empty();
  }

  public static Optional<String> findAttribute(final XMLStreamReader in, final String attribute) {
    return ofNullable(in.getAttributeValue("", attribute));
  }

  public static Optional<Integer> findIntegerAttribute(final String in, final String attribute) {
    if (findAttribute(in, attribute).isPresent()) {
      return ofNullable(parseInt(getAttribute(in, attribute)));
    }
    return empty();
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

  public static String getAttribute(final String in, final String attribute) {
    final Optional<String> foundOpt = findAttribute(in, attribute);
    if (foundOpt.isPresent()) {
      return foundOpt.get();
    }
    throw new RuntimeException("\"" + attribute + "\" not found in \"" + in + "\"");
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

  public static List<String> getChunks(
      final String in, final String includingStart, final String includingEnd) {
    final Pattern pattern =
        Pattern.compile("(" + includingStart + ".+?" + includingEnd + ")", DOTALL);
    final Matcher matcher = pattern.matcher(in);
    final List<String> chunks = new ArrayList<>();
    while (matcher.find()) {
      chunks.add(matcher.group());
    }
    return chunks;
  }

  public static String getContent(final String in, final String tag) {
    Pattern pattern =
        Pattern.compile(
            "<" + tag + "\\s?[^>]*>[^<]*<!\\[CDATA\\[(" + ".+?" + ")\\]\\]>[^<]*</" + tag + ">",
            DOTALL);
    Matcher matcher = pattern.matcher(in);
    if (matcher.find()) {
      return matcher.group(1);
    }
    pattern = Pattern.compile("<" + tag + "\\s?[^>]*>(" + ".+?" + ")</" + tag + ">", DOTALL);
    matcher = pattern.matcher(in);
    if (matcher.find()) {
      return matcher.group(1);
    }
    throw new RuntimeException("\"" + tag + "\" not found in " + in);
  }

  public static Integer getIntegerAttribute(final String in, final String attribute) {
    return parseInt(getAttribute(in, attribute));
  }

  public static Integer getIntegerAttribute(final XMLStreamReader in, final String attribute) {
    return parseInt(getAttribute(in, attribute));
  }

  public static Integer getIntegerContent(final String in, final String tag) {
    final String content = getContent(in, tag);
    return parseInt(content);
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

  private ViolationParserUtils() {}
}
