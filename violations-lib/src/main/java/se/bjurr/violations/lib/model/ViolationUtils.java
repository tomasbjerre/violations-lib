package se.bjurr.violations.lib.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressFBWarnings("PATH_TRAVERSAL_IN")
public final class ViolationUtils {

  private ViolationUtils() {}

  public static String relativePath(final List<Path> allFiles, final Violation v) {
    final String file = v.getFile();
    return relativePath(allFiles, file, getUserDir());
  }

  static String relativePath(final List<Path> allFiles, final String file, final String userDir) {
    final String userDirNotNull = Optional.ofNullable(userDir).orElse("");
    final String cwd = Violation.frontSlashes(userDirNotNull);
    final String relativeToCwd = getFileRelativeToCwd(allFiles, file);
    return removeSlashAtBeginning(relativeToCwd, cwd);
  }

  private static String getFileRelativeToCwd(List<Path> allFound, final String file) {
    allFound =
        allFound.stream()
            .filter((it) -> it.toFile().getAbsolutePath().endsWith(file))
            .collect(Collectors.toList());
    if (allFound.isEmpty() || allFound.size() > 1) {
      return file;
    }
    return allFound.get(0).toFile().getAbsolutePath();
  }

  private static String getUserDir() {
    return System.getProperty("user.dir");
  }

  public static List<Path> getAllFiles() {
    return getAllFiles(getUserDir());
  }

  public static List<Path> getAllFiles(final String cwd) {
    if (cwd == null) {
      return new ArrayList<>();
    }
    try (final Stream<Path> stream = Files.walk(Paths.get(cwd))) {
      return stream.filter(Files::isRegularFile).collect(Collectors.toList());
    } catch (final IOException e) {
      return new ArrayList<>();
    }
  }

  private static String removeSlashAtBeginning(final String file, final String cwd) {
    if (file.equals(Violation.NO_FILE)) {
      return file;
    }
    if (cwd == null || cwd.isEmpty()) {
      return removeAnySlashAtBeginning(file);
    }
    final String fileRemoved = removeAnySlashAtBeginning(file);
    final String cwdRemoved = removeAnySlashAtBeginning(cwd);
    if (fileRemoved.startsWith(cwdRemoved)) {
      return removeAnySlashAtBeginning(fileRemoved.substring(cwdRemoved.length()));
    }
    return fileRemoved;
  }

  private static String removeAnySlashAtBeginning(final String file) {
    if (file.startsWith("/")) {
      return file.substring(1);
    }
    return file;
  }
}
