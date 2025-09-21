package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.reports.Parser.XMLLINT;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class XMLLintTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/xmllint/.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(XMLLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(3);

    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .isEqualTo("Premature end of data in tag html line 1");
    assertThat(new ArrayList<>(actual).get(0).getFile()) //
        .isEqualTo("xml/other.xml");
    assertThat(new ArrayList<>(actual).get(0).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(new ArrayList<>(actual).get(0).getRule()) //
        .isEqualTo("parser error");
    assertThat(new ArrayList<>(actual).get(0).getStartLine()) //
        .isEqualTo(7);
    assertThat(new ArrayList<>(actual).get(0).getEndLine()) //
        .isEqualTo(7);

    assertThat(new ArrayList<>(actual).get(2).getMessage()) //
        .isEqualTo("Opening and ending tag mismatch: font line 4 and body");
    assertThat(new ArrayList<>(actual).get(2).getFile()) //
        .isEqualTo("xml/other.xml");
    assertThat(new ArrayList<>(actual).get(2).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(new ArrayList<>(actual).get(2).getRule()) //
        .isEqualTo("parser error");
    assertThat(new ArrayList<>(actual).get(2).getStartLine()) //
        .isEqualTo(5);
    assertThat(new ArrayList<>(actual).get(2).getEndLine()) //
        .isEqualTo(5);
  }
}
