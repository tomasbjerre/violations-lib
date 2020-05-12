package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.reports.Parser.JUNIT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class JUnitTest {
  @Test
  public void testThatViolationsCanBeParsedFromJunit1() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/junit\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    assertThat(actual.get(0).getSource()) //
        .isEqualTo("com.example.jenkinstest.ExampleUnitTest");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("com/example/jenkinstest/ExampleUnitTest.kt");
    assertThat(actual.get(0).getMessage()) //
        .startsWith("failTest4") //
        .contains("java.lang.AssertionError");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(ERROR);

    assertThat(actual.get(1).getSource()) //
        .isEqualTo("com.example.jenkinstest.ExampleUnitTest");
    assertThat(actual.get(1).getFile()) //
        .isEqualTo("com/example/jenkinstest/ExampleUnitTest.kt");
    assertThat(actual.get(1).getMessage()) //
        .startsWith("failTest5") //
        .contains("java.lang.AssertionError");
    assertThat(actual.get(1).getSeverity()) //
        .isEqualTo(ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsedFromJunit2() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(
                ".*/junit/TEST-org.jenkinsci.plugins.jvctb.perform.JvctbPerformerTest\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    assertThat(actual.get(0).getSource()) //
        .isEqualTo("org.jenkinsci.plugins.jvctb.perform.JvctbPerformerTest");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("org/jenkinsci/plugins/jvctb/perform/JvctbPerformerTest.java");
    assertThat(actual.get(0).getMessage()) //
        .startsWith("testThatAll") //
        .contains("nondada");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsedFromJunit3() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/junit/TESTS-TestSuites\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    assertThat(actual.get(0).getSource()) //
        .isEqualTo("ch.bdna.tsm.service.PollingServiceTest");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("ch/bdna/tsm/service/PollingServiceTest.java");
    assertThat(actual.get(0).getMessage()) //
        .isEqualTo("testServices : Missing CPU value");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(ERROR);
  }
}
