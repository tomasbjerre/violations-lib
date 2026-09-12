package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.CPD;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
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

  /**
   * A PMD CPD report contains one top level {@code <file path="..." totalNumberOfTokens="..."/>}
   * per scanned file. Those have no line attribute and must not be mistaken for duplication
   * occurrences.
   */
  @Test
  public void testThatPmdCpdFileInventoryIsIgnored() throws Exception {
    final String report = readReport("pmd7-cpd-report-with-file-inventory.xml");

    final Set<Violation> actual = CPD.getViolationsParser().parseReportOutput(report, null);

    assertThat(actual) //
        .hasSize(6);

    assertThat(actual) //
        .extracting(Violation::getFile) //
        .doesNotContain(
            "/example/project/model/src/main/java/AbstractEntity.java",
            "/example/project/model/src/main/java/AbstractStatsCdr.java",
            "/example/project/model/src/main/java/Account.java");

    assertThat(actual) //
        .extracting(Violation::getStartLine) //
        .containsExactlyInAnyOrder(40, 38, 61, 58, 47, 53);

    final Violation violation = withStartLine(actual, 40);
    assertThat(violation.getFile()) //
        .isEqualTo("/example/project/model/src/main/java/SiteComputedUsageCdr.java");
    assertThat(violation.getMessage()) //
        .startsWith("Duplicated code detected (36 lines, 143 tokens) found in 2 files:");
    assertThat(violation.getRule()) //
        .isEqualTo("Code Duplication");
    assertThat(violation.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation.getEndLine()) //
        .isEqualTo(75);
    assertThat(violation.getColumn()) //
        .isEqualTo(35);
    assertThat(violation.getEndColumn()) //
        .isEqualTo(16);
  }

  /**
   * The file inventory made the parser throw, and {@link
   * se.bjurr.violations.lib.reports.ViolationsFinder} logs and swallows that, so every duplication
   * in the report was lost without any error being reported to the caller.
   */
  @Test
  public void testThatPmdCpdFileInventoryDoesNotDiscardTheReport() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/cpd/pmd7-cpd-report-with-file-inventory\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPD) //
            .violations();

    assertThat(actual) //
        .hasSize(6);
  }

  /** PMD 7.0.0 reports have the file inventory but no namespace, so they take the old code path. */
  @Test
  public void testThatPmdCpdFileInventoryIsIgnoredWithoutNamespace() throws Exception {
    final String report = readReport("pmd7-cpd-report-no-namespace.xml");

    final Set<Violation> actual = CPD.getViolationsParser().parseReportOutput(report, null);

    assertThat(actual) //
        .hasSize(6);

    assertThat(actual) //
        .extracting(Violation::getFile) //
        .doesNotContain("/example/project/model/src/main/java/AbstractEntity.java");

    final Violation violation = withStartLine(actual, 40);
    assertThat(violation.getFile()) //
        .isEqualTo("/example/project/model/src/main/java/SiteComputedUsageCdr.java");
    assertThat(violation.getRule()) //
        .isEqualTo("DUPLICATION");
    assertThat(violation.getSeverity()) //
        .isEqualTo(WARN);
  }

  @Test
  public void testThatPmdCpdFileInventoryDoesNotDiscardTheReportWithoutNamespace() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/cpd/pmd7-cpd-report-no-namespace\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPD) //
            .violations();

    assertThat(actual) //
        .hasSize(6);
  }

  private static String readReport(final String name) throws Exception {
    return new String(
        Files.readAllBytes(Paths.get(getRootFolder(), "cpd", name)), StandardCharsets.UTF_8);
  }

  private static Violation withStartLine(final Set<Violation> violations, final int startLine) {
    for (final Violation violation : violations) {
      if (violation.getStartLine().equals(startLine)) {
        return violation;
      }
    }
    throw new AssertionError("No violation with start line " + startLine + " in " + violations);
  }
}
