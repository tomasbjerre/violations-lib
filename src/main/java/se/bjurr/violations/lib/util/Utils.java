package se.bjurr.violations.lib.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import se.bjurr.violations.lib.model.Violation;

public class Utils {
  public static <T> T checkNotNull(final T reference, final String errorMessage) {
    if (reference == null) {
      throw new NullPointerException(errorMessage);
    }
    return reference;
  }

  public static String emptyToNull(final String str) {
    if (str == null || str.isEmpty()) {
      return null;
    }
    return str;
  }

  public static String nullToEmpty(final String nullableReference) {
    return nullableReference == null ? "" : nullableReference;
  }

  public static <T> T firstNonNull(final T f, final T s) {
    if (f != null) {
      return f;
    }
    return s;
  }

  @SuppressWarnings("static-access")
  public static InputStream getResource(final String filename) {
    return Thread.currentThread().getContextClassLoader().getSystemResourceAsStream(filename);
  }

  public static boolean isNullOrEmpty(final String str) {
    return str == null || str.isEmpty();
  }

  @SuppressWarnings("resource")
  public static String toString(final InputStream inputStream) throws IOException {
    final Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
    final String result = scanner.hasNext() ? scanner.next() : "";
    scanner.close();
    inputStream.close();

    return result;
  }

  public static String toString(final URL resource) throws IOException {
    try {
      return toString(resource.openStream());
    } catch (final IOException e) {
      throw e;
    }
  }

  public static List<Violation> setReporter(
      final List<Violation> violations, final String reporter) {
    for (final Violation v : violations) {
      v.setReporter(reporter);
    }
    return violations;
  }
}
