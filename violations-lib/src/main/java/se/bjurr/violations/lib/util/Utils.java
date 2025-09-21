package se.bjurr.violations.lib.util;

import static se.bjurr.violations.lib.util.StringUtils.padRight;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;
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
    final Scanner scanner =
        new Scanner(inputStream, StandardCharsets.UTF_8.displayName()).useDelimiter("\\A");
    final String result = scanner.hasNext() ? scanner.next() : "";
    scanner.close();
    inputStream.close();

    return result;
  }

  @SuppressFBWarnings("URLCONNECTION_SSRF_FD")
  public static String toString(final URL resource) throws IOException {
    try {
      return toString(resource.openStream());
    } catch (final IOException e) {
      throw e;
    }
  }

  public static Set<Violation> setReporter(final Set<Violation> violations, final String reporter) {
    for (final Violation v : violations) {
      v.setReporter(reporter);
    }
    return violations;
  }

  public static void updateReadmeWithReporters() throws IOException {

    String reporters = "";
    final List<Reporter> sorted =
        Arrays.stream(Reporter.values())
            .sorted(Comparator.comparing((r1) -> r1.getName()))
            .collect(Collectors.toList());
    for (final Reporter reporter : sorted) {
      reporters +=
          "| "
              + padRight("[_" + reporter.getName() + "_](" + reporter.getUrl() + ") ", 85)
              + " | "
              + padRight("`" + reporter.getParser().name() + "`", 20)
              + " | "
              + reporter.getNote().replace("|", "\\|")
              + "\n";
    }

    final Path readmeFile = findReadmeFile(FileSystems.getDefault().getPath("."));
    if (readmeFile == null) {
      return;
    }
    final String content = new String(Files.readAllBytes(readmeFile), StandardCharsets.UTF_8);
    final String beginPart = "| Reporter | Parser | Notes";
    final String endPart =
        "Missing a format? Open an issue [here](https://github.com/tomasbjerre/violations-lib/issues)!";
    final int start = content.indexOf(beginPart);
    if (start == -1) {
      throw new RuntimeException(
          "Could not find:\n\n" + beginPart + "\n" + endPart + "\n\n in " + readmeFile.toString());
    }
    final int end = content.indexOf(endPart);
    final String beforePart = content.substring(0, start + beginPart.length());
    final String afterPart = content.substring(end);
    final String reportersPart = reporters.trim();
    final String stats =
        Parser.values().length + " parsers and " + Reporter.values().length + " reporters.";
    final String newContent =
        beforePart + "\n| --- | --- | ---\n" + reportersPart + "\n\n" + stats + "\n\n" + afterPart;

    Files.write(readmeFile, newContent.getBytes(StandardCharsets.UTF_8));
  }

  public static Path findReadmeFile(final Path file) throws IOException {
    if (file == null) {
      return null;
    }
    final Set<Path> files = new HashSet<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(file)) {
      for (final Path path : stream) {
        if (!Files.isDirectory(path)) {
          files.add(path);
        }
      }
    }
    for (final Path candidate : files) {
      final Path fileName = candidate.getFileName();
      if (fileName != null && fileName.toString().equals("README.md")) {
        return candidate;
      }
    }
    return findReadmeFile(file.getParent());
  }
}
