package se.bjurr.violations.lib;

import static com.google.common.collect.Lists.reverse;
import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.ORDERED_BY.FILE;
import static se.bjurr.violations.lib.ORDERED_BY.SEVERITY;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsAccumulatedReporterApi.violationsAccumulatedReporterApi;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Reporter.CHECKSTYLE;
import static se.bjurr.violations.lib.reports.Reporter.JSHINT;

import org.junit.Test;

public class AccumulatedTest {

 @Test
 public void testThatViolationsCanBeFiltered() {
  ViolationsAccumulatedReporterApi violationsAccumulatedReporterApi = getAccumulatedReporterApi();

  assertThat(violationsAccumulatedReporterApi//
    .withAtLeastSeverity(ERROR)//
    .orderedBy(FILE)//
    .violations())//
      .hasSize(1);

  assertThat(violationsAccumulatedReporterApi//
    .withAtLeastSeverity(WARN)//
    .orderedBy(FILE)//
    .violations())//
      .hasSize(8);

  assertThat(violationsAccumulatedReporterApi//
    .withAtLeastSeverity(INFO)//
    .orderedBy(FILE)//
    .violations())//
      .hasSize(10);
 }

 @Test
 public void testThatViolationsCanBeOrdered() {
  ViolationsAccumulatedReporterApi violationsAccumulatedReporterApi = getAccumulatedReporterApi();

  assertThat(violationsAccumulatedReporterApi//
    .withAtLeastSeverity(INFO)//
    .orderedBy(FILE)//
    .violations()//
    .get(0).getFile())//
      .isEqualTo("../../../web/js-file.js");

  assertThat(reverse(violationsAccumulatedReporterApi//
    .withAtLeastSeverity(INFO)//
    .orderedBy(FILE)//
    .violations())//
      .get(0).getFile())//
        .isEqualTo("/src/main/java/se/bjurr/violations/lib/example/OtherClass.java");

  assertThat(violationsAccumulatedReporterApi//
    .withAtLeastSeverity(INFO)//
    .orderedBy(SEVERITY)//
    .violations()//
    .get(0).getSeverity())//
      .isEqualTo(INFO);

  assertThat(reverse(violationsAccumulatedReporterApi//
    .withAtLeastSeverity(INFO)//
    .orderedBy(SEVERITY)//
    .violations())//
      .get(0).getSeverity())//
        .isEqualTo(ERROR);

 }

 private ViolationsAccumulatedReporterApi getAccumulatedReporterApi() {
  String rootFolder = getRootFolder();

  return violationsAccumulatedReporterApi()//
    .withViolationsReporterApiList(//
      violationsReporterApi() //
        .withPattern(".*/checkstyle/.*\\.xml$") //
        .inFolder(rootFolder) //
        .findAll(CHECKSTYLE) //
        .violations()//
    ) //
    .withViolationsReporterApiList(//
      violationsReporterApi() //
        .withPattern(".*/jshint/.*\\.xml$") //
        .inFolder(rootFolder) //
        .findAll(JSHINT) //
        .violations()//
  );
 }
}
