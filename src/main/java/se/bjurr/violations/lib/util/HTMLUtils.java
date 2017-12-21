package se.bjurr.violations.lib.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HTMLUtils {
  private HTMLUtils() {}

  public static String htmlDecode(String input) {
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
}
