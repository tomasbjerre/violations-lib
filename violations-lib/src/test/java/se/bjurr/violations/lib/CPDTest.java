package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.CPD;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class CPDTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/cpd/cpd-report.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPD) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .startsWith("$request->setBo");
    assertThat(new ArrayList<>(actual).get(0).getFile()) //
        .isEqualTo(
            "/home/goetas/gits/webservices/src/goetas/webservices/bindings/soap/transport/http/Http.php");
    assertThat(new ArrayList<>(actual).get(0).getSeverity()) //
        .isEqualTo(INFO);
    assertThat(new ArrayList<>(actual).get(0).getRule()) //
        .isEqualTo("DUPLICATION");
    assertThat(new ArrayList<>(actual).get(0).getStartLine()) //
        .isEqualTo(41);
    assertThat(new ArrayList<>(actual).get(0).getEndLine()) //
        .isEqualTo(41);

    assertThat(new ArrayList<>(actual).get(1).getMessage()) //
        .startsWith("$request->setBo");
  }

  @Test
  public void testThatNewPmdCpdFormatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/cpd/pmd-cpd-report.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPD) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    // The violations are sorted, so we need to check them based on their actual order
    // First violation (line 18)
    Violation firstViolation = null;
    Violation secondViolation = null;

    for (Violation violation : actual) {
      if (violation.getStartLine().equals(18)) {
        firstViolation = violation;
      } else if (violation.getStartLine().equals(178)) {
        secondViolation = violation;
      }
    }

    assertThat(firstViolation).isNotNull();
    assertThat(firstViolation.getMessage()) //
        .startsWith("Duplicated code detected (29 lines, 67 tokens) found in 2 files:");
    assertThat(firstViolation.getFile()) //
        .isEqualTo("/Users/xyz/AFSEViewControllerProtocol.swift");
    assertThat(firstViolation.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(firstViolation.getRule()) //
        .isEqualTo("Code Duplication");
    assertThat(firstViolation.getStartLine()) //
        .isEqualTo(18);
    assertThat(firstViolation.getEndLine()) //
        .isEqualTo(46);
    assertThat(firstViolation.getColumn()) //
        .isEqualTo(49);
    assertThat(firstViolation.getEndColumn()) //
        .isEqualTo(27);

    // Second violation (line 178)
    assertThat(secondViolation).isNotNull();
    assertThat(secondViolation.getMessage()) //
        .startsWith("Duplicated code detected (29 lines, 67 tokens) found in 2 files:");
    assertThat(secondViolation.getFile()) //
        .isEqualTo("/Users/xyz/AFSEViewControllerProtocol.swift");
    assertThat(secondViolation.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(secondViolation.getRule()) //
        .isEqualTo("Code Duplication");
    assertThat(secondViolation.getStartLine()) //
        .isEqualTo(178);
    assertThat(secondViolation.getEndLine()) //
        .isEqualTo(206);
    assertThat(secondViolation.getColumn()) //
        .isEqualTo(58);
    assertThat(secondViolation.getEndColumn()) //
        .isEqualTo(27);
  }
}
