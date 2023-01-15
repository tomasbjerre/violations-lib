package se.bjurr.violations.lib.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ViolationUtils {
  private ViolationUtils() {}

  public static String relativePath(final Violation v) {
    final String userDir = System.getProperty("user.dir");
    final String file = v.getFile();
    return relativePath(file, userDir);
  }

  @SuppressFBWarnings("PATH_TRAVERSAL_IN")
  static String relativePath(final String file, final String userDir) {
    final String userDirNotNull = Optional.ofNullable(userDir).orElse("");
    final String cwd = Violation.frontSlashes(userDirNotNull);
    final String relativeToCwd = getFileRelativeToCwd(cwd, file);
    return removeSlashAtBeginning(relativeToCwd, cwd);
  }

  private static String getFileRelativeToCwd(final String cwd, final String file) {
    List<Path> allFound;
    try (final Stream<Path> stream = Files.walk(Paths.get(cwd))) {
      allFound =
          stream
              .filter(Files::isRegularFile)
              .filter((it) -> it.toFile().getAbsolutePath().endsWith(file))
              .collect(Collectors.toList());
    } catch (final IOException e) {
      allFound = new ArrayList<>();
    }
    if (allFound.isEmpty() || allFound.size() > 1) {
      return file;
    }
    return allFound.get(0).toFile().getAbsolutePath();
  }

  private static String removeSlashAtBeginning(final String file, final String cwd) {
    final boolean isAbsolute = new File(file).isAbsolute();
    if (!isAbsolute || cwd.isEmpty()) {
      return removeAnySlashAtBeginning(file);
    }
    if (file.startsWith(cwd)) {
      final String relative = file.replaceFirst(cwd, "");
      return removeAnySlashAtBeginning(relative);
    }
    return file;
  }

  private static String removeAnySlashAtBeginning(final String file) {
    if (file.startsWith("/")) {
      return file.substring(1);
    }
    return file;
  }
}