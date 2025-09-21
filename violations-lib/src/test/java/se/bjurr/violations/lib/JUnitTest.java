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
        .startsWith("testServices : Missing CPU value");
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
        .startsWith(
            "testConcatenate4 : org.opentest4j.AssertionFailedError: expected: <onefive> but was: <onetwo>");
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
        .isEqualTo("timrAPITests/Tests/Utils/UtilTests.swift");
    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .isEqualTo(
            "testJoinStringsWithCustomSeparator : XCTAssertEqual failed: (\"\") is not equal to (\"TESTFAIL\") timrAPITests/Tests/Utils/UtilTests.swift:23");
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

  @Test
  public void testThatViolationsCanBeParsedFromJestJunit() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/jest-junit\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getFile())
        .isEqualTo("C:/workspace/whatever-template/src/components/TestFile.spec.ts");
    assertThat(violation0.getMessage())
        .startsWith("should not have any option checked by default : Error: expect(receive");
    assertThat(violation0.getSeverity()).isEqualTo(ERROR);

    final Violation violation1 = new ArrayList<>(actual).get(1);
    assertThat(violation1.getFile())
        .isEqualTo("C:/workspace/whatever-template/src/components/TestFile.spec.ts");
    assertThat(violation1.getMessage())
        .startsWith("should not have any option checked by default : Error: expect.assertions(3)");
    assertThat(violation1.getSeverity()).isEqualTo(ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsedFromJunit113() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/issue-113\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getFile()).isEqualTo("Assignment1Test.java");
    assertThat(violation0.getMessage())
        .startsWith("shouldDrawIBIntoEmptyWorld : java.lang.AssertionError");
    assertThat(violation0.getSeverity()).isEqualTo(ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsedFromJENKINS64117() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/JENKINS-64117\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getFile())
        .isEqualTo("eu/pinteam/kyoto/gunit/testenv/test/CalculationUtilTest.java");
    assertThat(violation0.getMessage()).startsWith("runCalculationScriptTest : The container");
    assertThat(violation0.getSeverity()).isEqualTo(ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsedFromArchUnit1() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/archunit1\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getFile()).isEqualTo("Aufgabe3Test.java");
    assertThat(violation0.getMessage())
        .startsWith("shouldSplitToEmptyRight(int)[1] : org.opentest4j.AssertionFailedError:");
    assertThat(violation0.getSeverity()).isEqualTo(ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsedFromArchUnit2() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/archunit2\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getFile()).isEqualTo("Aufgabe3Test.java");
    assertThat(violation0.getMessage())
        .startsWith("shouldSplitToEmptyRight(int)[1] : org.opentest4j.AssertionFailedError:");
    assertThat(violation0.getSeverity()).isEqualTo(ERROR);

    final Violation violation1 = new ArrayList<>(actual).get(1);
    assertThat(violation1.getFile()).isEqualTo("Integers.java");
    assertThat(violation1.getMessage()).startsWith("ALL_FINAL : java.lang.AssertionError");
    assertThat(violation1.getSeverity()).isEqualTo(ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsedFromArchUnit3() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/archunit3\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getFile()).isEqualTo("Integers.java");
    assertThat(violation0.getMessage()).startsWith("ALL_FINAL : java.lang.AssertionError");
    assertThat(violation0.getSeverity()).isEqualTo(ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsedFromPlain() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/plainerror\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getFile()).isEqualTo("-");
    assertThat(violation0.getStartLine()).isEqualTo(0);
    assertThat(violation0.getMessage())
        .isEqualTo(
            "shouldParseEmptyFile : org.json.JSONException: Missing value at 0 [character 1 line 1]");
    assertThat(violation0.getSeverity()).isEqualTo(ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsedFromCfn() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/cfn-lint\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getSource()) //
        .isEqualTo("");
    assertThat(violation0.getFile()) //
        .isEqualTo("-");
    assertThat(violation0.getMessage()) //
        .startsWith("E3012") //
        .contains(
            "E3012 Check resource properties values : Property Resources/ActivitiesTable/Properties/ProvisionedThroughput/ReadCapacityUnits should be of type Long at sam-python-crud-sample/template.yaml:142:9");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(ERROR);
  }
}
