package se.bjurr.violations.lib.model.codeclimate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class CodeClimateTransformer {
  public static List<CodeClimate> fromViolations(final Set<Violation> from) {
    return from.stream()
        .map(
            violation -> {
              return toCodeClimate(violation);
            })
        .collect(Collectors.toList());
  }

  private static CodeClimate toCodeClimate(final Violation v) {
    final String description = v.getMessage();
    final String fingerprint = toHash(v);
    final CodeClimateLines lines = new CodeClimateLines(v.getStartLine());
    final CodeClimateLocation location = new CodeClimateLocation(relativePath(v), lines, null);
    final CodeClimateSeverity severity = toSeverity(v.getSeverity());
    final String check_name = v.getRule().isEmpty() ? v.getReporter() : v.getRule();
    final String engine_name = v.getReporter();
    final List<CodeClimateCategory> categories = new ArrayList<>();
    categories.add(CodeClimateCategory.BUGRISK);
    return new CodeClimate(
        description, fingerprint, location, severity, check_name, engine_name, categories);
  }

  private static String relativePath(final Violation v) {
    final String userDir = System.getProperty("user.dir");
    final String file = v.getFile();
    return relativePath(file, userDir);
  }

  static String relativePath(final String file, final String userDir) {
    final String cwd = Violation.frontSlashes(Optional.ofNullable(userDir).orElse(""));
    if (!cwd.isEmpty() && file.contains(cwd)) {
      final String relative = file.replace(cwd, "");
      if (relative.startsWith("/")) {
        return relative.substring(1);
      }
      return relative;
    }
    return file;
  }

  private static CodeClimateSeverity toSeverity(final SEVERITY severity) {
    if (severity == SEVERITY.ERROR) {
      return CodeClimateSeverity.critical;
    }
    if (severity == SEVERITY.WARN) {
      return CodeClimateSeverity.minor;
    }
    return CodeClimateSeverity.info;
  }

  private static String toHash(final Violation v) {
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (final NoSuchAlgorithmException e) {
      return "No Hash: " + e.getMessage();
    }
    final String fingerprintString =
        v.getColumn()
            + relativePath(v)
            + v.getMessage()
            + v.getParser()
            + v.getReporter()
            + v.getRule()
            + v.getCategory()
            + v.getSeverity()
            + v.getSource()
            + v.getGroup();
    final byte[] encodedhash = digest.digest(fingerprintString.getBytes(StandardCharsets.UTF_8));
    final StringBuffer hexString = new StringBuffer();
    for (final byte element : encodedhash) {
      final String hex = String.format("%02X", element);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString().toLowerCase(Locale.ENGLISH);
  }
}
