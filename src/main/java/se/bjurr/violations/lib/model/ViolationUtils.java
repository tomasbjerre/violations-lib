package se.bjurr.violations.lib.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.util.Optional;

public final class ViolationUtils {
  private ViolationUtils() {}

  public static String relativePath(final Violation v) {
    final String userDir = System.getProperty("user.dir");
    final String file = v.getFile();
    return relativePath(file, userDir);
  }

  @SuppressFBWarnings("PATH_TRAVERSAL_IN")
  static String relativePath(final String file, final String userDir) {
    final String cwd = Violation.frontSlashes(Optional.ofNullable(userDir).orElse(""));
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
