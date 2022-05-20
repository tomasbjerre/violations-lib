package se.bjurr.violations.lib.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.owasp.encoder.Encode;

public final class StringUtils {
  private static final Map<String, Character> XML_ESCAPE_CHARACTER_MAP;
  private static final String[] XML_ESCAPE_CHARACTERS;
  private static final char XML_ESCAPE_START = '&';

  static {
    final Map<String, Character> temp = new ConcurrentHashMap<>();
    temp.put("&apos;", '\'');
    temp.put("&quot;", '\"');
    temp.put("&amp;", '&');
    temp.put("&lt;", '<');
    temp.put("&gt;", '>');
    XML_ESCAPE_CHARACTER_MAP = Collections.unmodifiableMap(temp);
    XML_ESCAPE_CHARACTERS = temp.keySet().toArray(new String[0]);
  }

  private StringUtils() {}

  public static String htmlDecode(final String input) {
    final Pattern p = Pattern.compile("&#(\\d+);");
    final Matcher m = p.matcher(input);
    final StringBuffer sb = new StringBuffer();
    while (m.find()) {
      final Integer found = Integer.valueOf(m.group(1));
      final String character = String.valueOf((char) found.intValue());
      m.appendReplacement(sb, character);
    }
    m.appendTail(sb);
    return sb.toString();
  }

  public static String xmlDecode(final String input) {
    final StringBuilder result = new StringBuilder(input.length());
    for (int i = 0; i < input.length(); ) {
      final char current = input.charAt(i);

      boolean isValidXmlEscape = false;
      if (current == XML_ESCAPE_START) {
        for (final String escapeCharacter : XML_ESCAPE_CHARACTERS) {
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

  public static String escapeHTML(final String s) {
    return Encode.forHtml(s);
  }

  @SuppressFBWarnings
  public static String padRight(final String s, final int n) {
    return String.format("%1$-" + n + "s", s);
  }
}
