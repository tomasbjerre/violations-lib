package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.JCREPORT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class JCReportTest {
  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/jcreport/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(JCREPORT) //
            .violations();

    assertThat(actual) //
        .hasSize(54);

    assertThat(actual.get(0).getMessage()) //
        .isEqualTo(
            "Comparator method org.jcoderz.guidelines.snippets.SampleSnippets$IndentionSample.compareTo(Object) doesn't seem to return all ordering values");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo(
            "D:/projects/fawkez/src/java/org/jcoderz/guidelines/snippets/SampleSnippets.java");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(WARN);
    assertThat(actual.get(0).getRule()) //
        .isEqualTo("SC_SUSPICIOUS_COMPARATOR_RETURN_VALUES(Findbugs)");
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(333);
    assertThat(actual.get(0).getEndLine()) //
        .isEqualTo(333);

    assertThat(actual.get(1).getMessage()) //
        .isEqualTo(
            "org.jcoderz.guidelines.snippets.SampleSnippets$IndentionSample defines compareTo(Object) and uses Object.equals()");
  }
}
