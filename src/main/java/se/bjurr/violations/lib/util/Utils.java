package se.bjurr.violations.lib.util;

import static se.bjurr.violations.lib.util.StringUtils.padRight;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Reporter;

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

  public static void updateReadmeWithReporters() throws IOException {

    String reporters = "";
    Set<Reporter> sorted = new TreeSet<>(Arrays.asList(Reporter.values()));
    for (Reporter reporter : sorted) {
      reporters +=
          "| "
              + padRight("[_" + reporter.getName() + "_](" + reporter.getUrl() + ") ", 85)
              + " | "
              + padRight("`" + reporter.getParser().name() + "`", 20)
              + " | "
              + reporter.getNote()
              + "\n";
    }

    File readmeFile = findReadmeFile(new File("."));
    String content = new String(Files.readAllBytes(readmeFile.toPath()), Charset.forName("UTF-8"));
    final String beginPart = "| Reporter | Parser | Notes";
    final String endPart =
        "Missing a format? Open an issue [here](https://github.com/tomasbjerre/violations-lib/issues)!";
    final int start = content.indexOf(beginPart);
    if (start == -1) {
      throw new RuntimeException(
          "Could not find:\n\n"
              + beginPart
              + "\n"
              + endPart
              + "\n\n in "
              + readmeFile.getAbsolutePath());
    }
    final int end = content.indexOf(endPart);
    final String beforePart = content.substring(0, start + beginPart.length());
    final String afterPart = content.substring(end);
    final String reportersPart = reporters.trim();
    String newContent = beforePart + "\n| --- | --- | ---\n" + reportersPart + "\n\n" + afterPart;

    Files.write(readmeFile.toPath(), newContent.getBytes(Charset.forName("UTF-8")));
  }

  public static File findReadmeFile(File file) {
    for (File candidate : file.listFiles()) {
      if (candidate.getName().equals("README.md")) {
        return candidate;
      }
    }

    return findReadmeFile(file.getParentFile());
  }
}
