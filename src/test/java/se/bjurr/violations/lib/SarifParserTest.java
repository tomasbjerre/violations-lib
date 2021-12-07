package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Parser.SARIFPARSER;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class SarifParserTest {

  @Test
  public void testThatViolationsCanBeParsed_smoke() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/sarif/.*(sarif|json)$") //
            .inFolder(rootFolder) //
            .findAll(SARIFPARSER) //
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
            .findAll(SARIFPARSER) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation first = new ArrayList<>(actual).get(0);
    assertThat(first.getMessage()) //
        .isEqualTo("'x' is assigned a value but never used.");
    assertThat(first.getFile()) //
        .isEqualTo("file:///C:/dev/sarif/sarif-tutorials/samples/Introduction/simple-example.js");
    assertThat(first.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(first.getRule()) //
        .isEqualTo("no-unused-vars");
    assertThat(first.getStartLine()) //
        .isEqualTo(1);
    assertThat(first.getEndLine()) //
        .isEqualTo(1);
  }
}
