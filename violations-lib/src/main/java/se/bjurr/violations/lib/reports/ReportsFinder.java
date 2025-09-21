package se.bjurr.violations.lib.reports;

import static java.nio.file.Files.walkFileTree;
import static java.util.logging.Level.FINE;
import static java.util.regex.Pattern.matches;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import se.bjurr.violations.lib.ViolationsLogger;

public class ReportsFinder {

  public static List<File> findAllReports(
      final ViolationsLogger violationsLogger,
      final File startFile,
      final String pattern,
      final List<String> ignorePaths) {
    final List<File> found = new ArrayList<>();
    final Path startPath = startFile.toPath();
    try {
      walkFileTree(
          startPath,
          new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
                throws IOException {
              final String relativePath = startPath.relativize(file).toString();
              if (isIgnored(relativePath, ignorePaths)) {
                return super.visitFile(file, attrs);
              }

              final String absolutePath = file.toFile().getAbsolutePath();
              if (matches(pattern, absolutePath)
                  || matches(pattern, withFrontSlashes(absolutePath))) {
                violationsLogger.log(FINE, pattern + " matches " + absolutePath);
                found.add(file.toFile());
              } else {
                violationsLogger.log(FINE, pattern + " does not match " + absolutePath);
              }
              return super.visitFile(file, attrs);
            }
          });
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
    Collections.sort(found);
    return found;
  }

  static String withFrontSlashes(final String file) {
    return file.replace('\\', '/');
  }

  public static boolean isIgnored(final String path, final List<String> ignorePaths) {
    for (final String ignorePath : ignorePaths) {
      if (withFrontSlashes(path).startsWith(withFrontSlashes(ignorePath))) {
        return true;
      }
    }
    return false;
  }
}
