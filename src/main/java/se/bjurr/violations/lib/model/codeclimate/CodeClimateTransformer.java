package se.bjurr.violations.lib.model.codeclimate;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.model.ViolationUtils;

public class CodeClimateTransformer {
  public static List<CodeClimate> fromViolations(final Set<Violation> from) {
    final List<Path> allFiles = ViolationUtils.getAllFiles();
    final List<CodeClimate> codeClimates =
        from.stream()
            .map(
                violation -> {
                  return toCodeClimate(allFiles, violation);
                })
            .collect(Collectors.toList());

    final Map<String, CodeClimate> codeClimatesPerFingerprint = new TreeMap<>();
    for (final CodeClimate candidate : codeClimates) {
      if (codeClimatesPerFingerprint.containsKey(candidate.getFingerprint())) {
        final CodeClimate existing = codeClimatesPerFingerprint.get(candidate.getFingerprint());
        existing.getOther_locations().add(candidate.getLocation());
      } else {
        codeClimatesPerFingerprint.put(candidate.getFingerprint(), candidate);
      }
    }
    return new ArrayList<CodeClimate>(codeClimatesPerFingerprint.values());
  }

  private static CodeClimate toCodeClimate(final List<Path> allFiles, final Violation v) {
    final String description = v.getMessage();
    final String fingerprint = toHash(allFiles, v);
    final CodeClimateLines lines = new CodeClimateLines(v.getStartLine());
    final CodeClimateLocation location =
        new CodeClimateLocation(ViolationUtils.relativePath(allFiles, v), lines, null);
    final CodeClimateSeverity severity = toSeverity(v.getSeverity());
    final String check_name = v.getRule().isEmpty() ? v.getReporter() : v.getRule();
    final String engine_name = v.getReporter();
    final List<CodeClimateCategory> categories = new ArrayList<>();
    categories.add(CodeClimateCategory.BUGRISK);
    return new CodeClimate(
        description,
        fingerprint,
        location,
        severity,
        check_name,
        engine_name,
        categories,
        new ArrayList<CodeClimateLocation>());
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

  private static String toHash(final List<Path> allFiles, final Violation v) {
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (final NoSuchAlgorithmException e) {
      return "No Hash: " + e.getMessage();
    }
    final String fingerprintString =
        v.getColumn()
            + ViolationUtils.relativePath(allFiles, v)
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
