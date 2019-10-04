package se.bjurr.violations.lib.reports;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

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
}
