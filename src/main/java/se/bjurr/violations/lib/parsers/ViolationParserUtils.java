package se.bjurr.violations.lib.parsers;

import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.quote;
import static se.bjurr.violations.lib.util.Optional.absent;
import static se.bjurr.violations.lib.util.Optional.fromNullable;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import se.bjurr.violations.lib.util.Optional;

public final class ViolationParserUtils {
  private static final Map<String, Character> XML_ESCAPE_CHARACTER_MAP;
  private static final String[] XML_ESCAPE_CHARACTERS;
  private static final char XML_ESCAPE_START = '&';

  static {
    Map<String, Character> temp = new HashMap<>();
    temp.put("&apos;", '\'');
    temp.put("&quot;", '\"');
    temp.put("&amp;", '&');
    temp.put("&lt;", '<');
    temp.put("&gt;", '>');
    XML_ESCAPE_CHARACTER_MAP = Collections.unmodifiableMap(temp);
    XML_ESCAPE_CHARACTERS = temp.keySet().toArray(new String[0]);
  }

  private static String unEscapeXml(String input) {
    StringBuilder result = new StringBuilder(input.length());
    for (int i = 0; i < input.length(); ) {
      char current = input.charAt(i);

      boolean isValidXmlEscape = false;
      if (current == XML_ESCAPE_START) {
        for (String escapeCharacter : XML_ESCAPE_CHARACTERS) {
          if (input.startsWith(escapeCharacter, i)) {
            result.append(XML_ESCAPE_CHARACTER_MAP.get(escapeCharacter));
            i += escapeCharacter.length();
            isValidXmlEscape = true;
            break;
          }
        }
      }

      if (!isValidXmlEscape) {
        result.append(current);
        i++;
      }
    }
    return result.toString();
  }

  public static String asString(XMLStreamReader xmlr) throws Exception {
    final Transformer transformer = TransformerFactory.newInstance().newTransformer();
    final StringWriter stringWriter = new StringWriter();
    transformer.transform(new StAXSource(xmlr), new StreamResult(stringWriter));
    return stringWriter.toString();
  }

  public static Optional<String> findAttribute(String in, String attribute) {
    Pattern pattern = Pattern.compile(attribute + "='([^']+?)'");
    Matcher matcher = pattern.matcher(in);
    if (matcher.find()) {
      return fromNullable(unEscapeXml(matcher.group(1)));
    }
    pattern = Pattern.compile(attribute + "=\"([^\"]+?)\"");
    matcher = pattern.matcher(in);
    if (matcher.find()) {
      return fromNullable(unEscapeXml(matcher.group(1)));
    }
    return absent();
  }

  public static Optional<String> findAttribute(XMLStreamReader in, String attribute) {
    return fromNullable(in.getAttributeValue("", attribute));
  }

  public static Optional<Integer> findIntegerAttribute(String in, String attribute) {
    if (findAttribute(in, attribute).isPresent()) {
      return fromNullable(parseInt(getAttribute(in, attribute)));
    }
    return absent();
  }

  public static Optional<Integer> findIntegerAttribute(XMLStreamReader in, String attribute) {
    final String attr = in.getAttributeValue("", attribute);
    if (attr == null) {
      return Optional.absent();
    } else {
      return fromNullable(Integer.parseInt(attr));
    }
  }

  public static String getAttribute(String in, String attribute) {
    final Optional<String> foundOpt = findAttribute(in, attribute);
    if (foundOpt.isPresent()) {
      return foundOpt.get();
    }
    throw new RuntimeException("\"" + attribute + "\" not found in \"" + in + "\"");
  }

  public static String getAttribute(XMLStreamReader in, String attribute) {
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

  public static List<String> getChunks(String in, String includingStart, String includingEnd) {
    final Pattern pattern =
        Pattern.compile("(" + includingStart + ".+?" + includingEnd + ")", DOTALL);
    final Matcher matcher = pattern.matcher(in);
    final List<String> chunks = new ArrayList<>();
    while (matcher.find()) {
      chunks.add(matcher.group());
    }
    return chunks;
  }

  public static String getContent(String in, String tag) {
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

  public static Integer getIntegerAttribute(String in, String attribute) {
    return parseInt(getAttribute(in, attribute));
  }

  public static Integer getIntegerAttribute(XMLStreamReader in, String attribute) {
    return parseInt(getAttribute(in, attribute));
  }

  public static Integer getIntegerContent(String in, String tag) {
    final String content = getContent(in, tag);
    return parseInt(content);
  }

  public static List<String> getLines(String string) {
    return Arrays.asList(string.split("\n"));
  }

  /** @return List per line in String, with groups from regexpPerLine. */
  public static List<List<String>> getLines(String string, String regexpPerLine) {
    final List<List<String>> results = new ArrayList<>();
    final Pattern pattern = Pattern.compile(regexpPerLine);
    for (final String line : string.split("\n")) {
      final Matcher matcher = pattern.matcher(line);
      if (!matcher.find()) {
        continue;
      }
      final List<String> lineParts = new ArrayList<>();
      for (int g = 0; g <= matcher.groupCount(); g++) {
        lineParts.add(matcher.group(g));
      }
      results.add(lineParts);
    }
    return results;
  }

  /**
   * Match one regexp at a time. Remove the matched part from the string, trim, and match next
   * regexp on that string...
   */
  public static List<String> getParts(String string, String... regexpList) {
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
