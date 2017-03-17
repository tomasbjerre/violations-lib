package se.bjurr.violations.lib.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Reporter.CHECKSTYLE;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class FilteringTest {

  @Test
  public void testFilteringINFO() {
    List<Violation> unfiltered =
        newArrayList( //
            violationBuilder() //
                .setFile("file") //
                .setStartLine(2) //
                .setMessage("message") //
                .setReporter(CHECKSTYLE) //
                .setSeverity(INFO) //
                .build() //
            );

    assertThat(Filtering.withAtLEastSeverity(unfiltered, ERROR)) //
        .hasSize(0);
    assertThat(Filtering.withAtLEastSeverity(unfiltered, WARN)) //
        .hasSize(0);
    assertThat(Filtering.withAtLEastSeverity(unfiltered, INFO)) //
        .hasSize(1);
  }

  @Test
  public void testFilteringWARN() {
    List<Violation> unfiltered =
        newArrayList( //
            violationBuilder() //
                .setFile("file") //
                .setStartLine(2) //
                .setMessage("message") //
                .setReporter(CHECKSTYLE) //
                .setSeverity(WARN) //
                .build() //
            );

    assertThat(Filtering.withAtLEastSeverity(unfiltered, ERROR)) //
        .hasSize(0);
    assertThat(Filtering.withAtLEastSeverity(unfiltered, WARN)) //
        .hasSize(1);
    assertThat(Filtering.withAtLEastSeverity(unfiltered, INFO)) //
        .hasSize(1);
  }

  @Test
  public void testFilteringERROR() {
    List<Violation> unfiltered =
        newArrayList( //
            violationBuilder() //
                .setFile("file") //
                .setStartLine(2) //
                .setMessage("message") //
                .setReporter(CHECKSTYLE) //
                .setSeverity(ERROR) //
                .build() //
            );

    assertThat(Filtering.withAtLEastSeverity(unfiltered, ERROR)) //
        .hasSize(1);
    assertThat(Filtering.withAtLEastSeverity(unfiltered, WARN)) //
        .hasSize(1);
    assertThat(Filtering.withAtLEastSeverity(unfiltered, INFO)) //
        .hasSize(1);
  }
}
