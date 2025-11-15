package se.bjurr.violations.lib.reports;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.reports.ReportsFinder.findAllReports;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.ViolationsLogger;

public class ReportsFinderTest {

  @Test
  public void testThatRegexpIsAlwaysMatchedAgainsFrontSlashes() {
    assertThat(ReportsFinder.withFrontSlashes("C:\\any\\thing")) //
        .isEqualTo("C:/any/thing");
    assertThat(ReportsFinder.withFrontSlashes("C:\\any\\thing.zip")) //
        .isEqualTo("C:/any/thing.zip");
    assertThat(ReportsFinder.withFrontSlashes("/c/any/thing")) //
        .isEqualTo("/c/any/thing");
    assertThat(ReportsFinder.withFrontSlashes("/c/any/thing.zip")) //
        .isEqualTo("/c/any/thing.zip");
  }

  @Test
  public void testThatFilesAreVisitedRecursively() throws URISyntaxException {
    final Level originalLevel = Logger.getLogger("").getLevel();
    Logger.getLogger("").setLevel(Level.FINE);
    Logger.getLogger("")
        .addHandler(
            new Handler() {

              @Override
              public void publish(final LogRecord record) {
                System.out.println(record.getMessage());
              }

              @Override
              public void flush() {}

              @Override
              public void close() throws SecurityException {}
            });
    final File path =
        Paths.get(ReportsFinderTest.class.getResource("/root.txt").toURI()).getParent().toFile();

    final ViolationsLogger violationsLogger =
        new ViolationsLogger() {

          @Override
          public void log(final Level level, final String string, final Throwable t) {
            Logger.getLogger(ReportsFinderTest.class.getSimpleName()).log(level, string, t);
          }

          @Override
          public void log(final Level level, final String string) {
            Logger.getLogger(ReportsFinderTest.class.getSimpleName()).log(level, string);
          }
        };
    assertThat(
            findAllReports(violationsLogger, path, ".*test-traversal.*txt$", new ArrayList<>())
                .stream()
                .map(
                    it -> {
                      return it.getName();
                    })
                .collect(toList()))
        .containsOnly("file-in-subdir.txt", "file-in-subdir2.txt", "file-in-subdir3.txt");

    assertThat(
            findAllReports(violationsLogger, path, ".*subdir3\\.txt", new ArrayList<>()).stream()
                .map(
                    it -> {
                      return it.getName();
                    })
                .collect(toList()))
        .containsOnly("file-in-subdir3.txt");

    Logger.getLogger("").setLevel(originalLevel);
  }
}
