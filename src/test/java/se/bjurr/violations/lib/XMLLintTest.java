package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.reports.Reporter.XMLLINT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class XMLLintTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsReporterApi() //
            .withPattern(".*/xmllint/.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(XMLLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(3);

    assertThat(actual.get(0).getMessage()) //
        .isEqualTo("Opening and ending tag mismatch: font line 4 and body");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("xml/other.xml");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actual.get(0).getRule().get()) //
        .isEqualTo("parser error");
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(5);
    assertThat(actual.get(0).getEndLine()) //
        .isEqualTo(5);

    assertThat(actual.get(2).getMessage()) //
        .isEqualTo("Premature end of data in tag html line 1");
    assertThat(actual.get(2).getFile()) //
        .isEqualTo("xml/other.xml");
    assertThat(actual.get(2).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actual.get(2).getRule().get()) //
        .isEqualTo("parser error");
    assertThat(actual.get(2).getStartLine()) //
        .isEqualTo(7);
    assertThat(actual.get(2).getEndLine()) //
        .isEqualTo(7);
  }
}
