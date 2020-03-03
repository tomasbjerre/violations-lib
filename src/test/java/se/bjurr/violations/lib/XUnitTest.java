package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.reports.Parser.XUNIT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class XUnitTest {
  @Test
  public void testThatViolationsCanBeParsedFromStylelint() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/xunit/stylelint-test-results.txt$") //
            .inFolder(rootFolder) //
            .findAll(XUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    assertThat(actual.get(0).getMessage()) //
        .startsWith("Expected no more than 1 empty line");

    assertThat(actual.get(1).getMessage()) //
        .startsWith("Unexpected whitespace at end of line (no-eol-whitespace)");
    assertThat(actual.get(1).getFile()) //
        .isEqualTo(
            "C:/Github/jenkins_test/org_newbalance/cartridges/org_newbalance/cartridge/client/default/scss/product/detail.scss");
    assertThat(actual.get(1).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actual.get(1).getRule()) //
        .isEqualTo("no-eol-whitespace");
    assertThat(actual.get(1).getStartLine()) //
        .isEqualTo(0);
    assertThat(actual.get(1).getEndLine()) //
        .isEqualTo(0);
  }

  @Test
  public void testThatViolationsCanBeParsedFromMocha() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/xunit/mocha-test-results.txt$") //
            .inFolder(rootFolder) //
            .findAll(XUNIT) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    Violation violation = actual.get(0);
    assertThat(violation.getMessage()) //
        .startsWith("expected 'success' to equal 'succes'");
    assertThat(violation.getFile()) //
        .isEqualTo(
            "C:/Github/jenkins_test/org_newbalance/test/unit/org_newbalance/scripts/hooks/fraudDetection.js");
    assertThat(violation.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation.getRule()) //
        .isEqualTo("fraud detection hook should return a success object");
    assertThat(violation.getStartLine()) //
        .isEqualTo(0);
    assertThat(violation.getEndLine()) //
        .isEqualTo(0);
  }
}
