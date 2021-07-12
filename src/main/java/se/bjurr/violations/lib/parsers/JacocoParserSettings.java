package se.bjurr.violations.lib.parsers;

public class JacocoParserSettings {
  public static final int DEFAULT_MIN_LINE_COUNT = 4;
  public static final double DEFAULT_MIN_COVERAGE = 0.70;

  private final int minLineCount;
  private final double minCoverage;

  public JacocoParserSettings() {
    this(DEFAULT_MIN_LINE_COUNT, DEFAULT_MIN_COVERAGE);
  }

  public JacocoParserSettings(final int minLineCount, final double minCoverage) {
    this.minLineCount = minLineCount;
    this.minCoverage = minCoverage;
  }

  public double getMinCoverage() {
    return this.minCoverage;
  }

  public int getMinLineCount() {
    return this.minLineCount;
  }
}
