package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.reports.Parser.JUNIT;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class JUnitTest {
  @Test
  public void testThatViolationsCanBeParsedFromJunit1() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/junit\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    assertThat(new ArrayList<>(actual).get(0).getSource()) //
        .isEqualTo("com.example.jenkinstest.ExampleUnitTest");
    assertThat(new ArrayList<>(actual).get(0).getFile()) //
        .isEqualTo("com/example/jenkinstest/ExampleUnitTest.kt");
    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .startsWith("failTest4") //
        .contains("java.lang.AssertionError");
    assertThat(new ArrayList<>(actual).get(0).getSeverity()) //
        .isEqualTo(ERROR);

    assertThat(new ArrayList<>(actual).get(1).getSource()) //
        .isEqualTo("com.example.jenkinstest.ExampleUnitTest");
    assertThat(new ArrayList<>(actual).get(1).getFile()) //
        .isEqualTo("com/example/jenkinstest/ExampleUnitTest.kt");
    assertThat(new ArrayList<>(actual).get(1).getMessage()) //
        .startsWith("failTest5") //
        .contains("java.lang.AssertionError");
    assertThat(new ArrayList<>(actual).get(1).getSeverity()) //
        .isEqualTo(ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsedFromJunit2() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(
                ".*/junit/TEST-org.jenkinsci.plugins.jvctb.perform.JvctbPerformerTest\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    assertThat(new ArrayList<>(actual).get(0).getSource()) //
        .isEqualTo("org.jenkinsci.plugins.jvctb.perform.JvctbPerformerTest");
    assertThat(new ArrayList<>(actual).get(0).getFile()) //
        .isEqualTo("org/jenkinsci/plugins/jvctb/perform/JvctbPerformerTest.java");
    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .startsWith("testThatAll") //
        .contains("nondada");
    assertThat(new ArrayList<>(actual).get(0).getSeverity()) //
        .isEqualTo(ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsedFromJunit3() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/TESTS-TestSuites\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    assertThat(new ArrayList<>(actual).get(0).getSource()) //
        .isEqualTo("ch.bdna.tsm.service.PollingServiceTest");
    assertThat(new ArrayList<>(actual).get(0).getFile()) //
        .isEqualTo("ch/bdna/tsm/service/PollingServiceTest.java");
    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .isEqualTo("testServices : Missing CPU value");
    assertThat(new ArrayList<>(actual).get(0).getSeverity()) //
        .isEqualTo(ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsedFromJunitWithoutMessage() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/without-message-with-type\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    assertThat(new ArrayList<>(actual).get(0).getSource()) //
        .isEqualTo("de.tobiasmichael.me.MyTest");
    assertThat(new ArrayList<>(actual).get(0).getFile()) //
        .isEqualTo("de/tobiasmichael/me/MyTest.java");
    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .isEqualTo("testConcatenate4 : org.opentest4j.AssertionFailedError");
    assertThat(new ArrayList<>(actual).get(0).getSeverity()) //
        .isEqualTo(ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsedFromJunitWithoutMessageAndType() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/junit-no-message-or-type\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    assertThat(new ArrayList<>(actual).get(0).getSource()) //
        .isEqualTo("timrAPITests.UtilTests");
    assertThat(new ArrayList<>(actual).get(0).getFile()) //
        .isEqualTo("timrAPITests/UtilTests.swift");
    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .isEqualTo(
            "testJoinStringsWithCustomSeparator : XCTAssertEqual failed: (\"\") is not equal to (\"TESTFAIL\")");
    assertThat(new ArrayList<>(actual).get(0).getSeverity()) //
        .isEqualTo(ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsedFromJunit4() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/junit2\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getSource()) //
        .isEqualTo("my.company.MainActivityTest");
    assertThat(violation0.getFile()) //
        .isEqualTo("my/company/MainActivityTest.java");
    assertThat(violation0.getMessage()) //
        .startsWith(
            "openAppInfoTest : androidx.test.espresso.AppNotIdleException: Looped for 3838 iterations over 60 SECONDS. The following Idle Conditions failed .");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(ERROR);
  }
}
