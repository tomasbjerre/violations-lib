package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.reports.Parser.SARIF;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class SarifParserTest {

  @Test
  public void testThatViolationsCanBeParsed_smoke() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/sarif/samples/.*(sarif|json)$") //
            .inFolder(rootFolder) //
            .findAll(SARIF) //
            .violations();

    assertThat(actual) //
        .hasSize(19);
  }

  @Test
  public void testThatViolationsCanBeParsed_simple_example() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/sarif/.*/simple-example.sarif$") //
            .inFolder(rootFolder) //
            .findAll(SARIF) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation first = new ArrayList<>(actual).get(0);
    assertThat(first.getMessage()) //
        .isEqualTo("'x' is assigned a value but never used.");
    assertThat(first.getFile()) //
        .isEqualTo("file:///C:/dev/sarif/sarif-tutorials/samples/Introduction/simple-example.js");
    assertThat(first.getSeverity()) //
        .isEqualTo(SEVERITY.ERROR);
    assertThat(first.getRule()) //
        .isEqualTo("no-unused-vars");
    assertThat(first.getStartLine()) //
        .isEqualTo(1);
    assertThat(first.getEndLine()) //
        .isEqualTo(1);
    assertThat(first.getReporter()).isEqualTo("ESLint");
  }

  @Test
  public void testThatViolationsCanBeParsed_result_line_nr() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/sarif/result_line_nr.json$") //
            .inFolder(rootFolder) //
            .findAll(SARIF) //
            .violations();

    assertThat(actual) //
        .hasSize(439);

    final Violation first = new ArrayList<>(actual).get(0);
    assertThat(first.getMessage()) //
        .isEqualTo(
            "'unsigned char' doesn't provide information about its size. Define and use typedefs clarifying type and size for numerical types or use one of the exact-width numerical types defined in <stdint.h>.\n\nFor additional help see: [How to resolve polyspace misra-c-2812](https://www.mathworks.com/matlabcentral/answers/479913-how-to-resolve-polyspace-misra-c-2012-d4-14-rule-when-am-passing-pointer-as-parameter-to-function)");
    assertThat(first.getFile()) //
        .isEqualTo(
            "file:/c:/var/lib/jenkins/workspace/PSBF_PIControl_DeclPipeline_Access/pi_alg/pi_alg.c");
    assertThat(first.getSeverity()) //
        .isEqualTo(SEVERITY.ERROR);
    assertThat(first.getRule()) //
        .isEqualTo("MISRA C:2012 D4.6");
    assertThat(first.getStartLine()) //
        .isEqualTo(144);
    assertThat(first.getEndLine()) //
        .isEqualTo(144);
    assertThat(first.getReporter()).isEqualTo("Polyspace");
  }

  @Test
  public void testThatViolationsCanBeParsed_securityscan() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/sarif/security-scan.json$") //
            .inFolder(rootFolder) //
            .findAll(SARIF) //
            .violations();

    assertThat(actual) //
        .hasSize(51);

    final List<Violation> arrayList = new ArrayList<>(actual);

    final Violation first = arrayList.get(0);
    assertThat(first.getMessage()) //
        .isEqualTo("The cookie is missing 'HttpOnly' flag.");
    assertThat(first.getFile()) //
        .isEqualTo("DummyFile.cs");
    assertThat(first.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);

    final String severities =
        actual.stream()
            .map(Violation::getSeverity)
            .map(it -> it.name())
            .collect(Collectors.joining(","));
    assertThat(severities) //
        .isEqualTo(
            "WARN,WARN,ERROR,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,INFO,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,INFO,WARN,WARN,WARN,WARN,WARN,WARN,WARN,ERROR,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,INFO,WARN,ERROR");
  }


  @Test
  public void testThatViolationsCanBeParsed_without_location() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/sarif/without-location.json$") //
            .inFolder(rootFolder) //
            .findAll(SARIF) //
            .violations();

    assertThat(actual) //
        .hasSize(0);
  }
}
