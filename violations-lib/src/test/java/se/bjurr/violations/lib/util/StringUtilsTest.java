package se.bjurr.violations.lib.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringUtilsTest {

  @Test
  public void escapeHTML() {
    String data = "\n";
    String expected = "\n";
    String results = StringUtils.escapeHTML(data);
    assertTrue(expected.equals(results));

    data = "<";
    expected = "&lt;";
    results = StringUtils.escapeHTML(data);
    assertTrue(expected.equals(results));
  }
}
