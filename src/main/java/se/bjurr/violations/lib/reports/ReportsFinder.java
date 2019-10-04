package se.bjurr.violations.lib.reports;

import static java.nio.file.Files.walkFileTree;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportsFinder {

  private static Logger LOG = Logger.getLogger(ReportsFinder.class.getSimpleName());

  public static List<File> findAllReports(final File startFile, final String pattern) {
    final List<File> found = new ArrayList<>();
    final Path startPath = startFile.toPath();
    try {
      walkFileTree(
          startPath,
          new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
                throws IOException {
              final String absolutePath = file.toFile().getAbsolutePath();
              if (matches(pattern, absolutePath)
                  || matches(pattern, withFrontSlashes(absolutePath))) {
                if (LOG.isLoggable(Level.FINE)) {
                  LOG.log(Level.FINE, pattern + " matches " + absolutePath);
                }
                found.add(file.toFile());
              } else {
                if (LOG.isLoggable(Level.FINE)) {
                  LOG.log(Level.FINE, pattern + " does not match " + absolutePath);
                }
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
}
