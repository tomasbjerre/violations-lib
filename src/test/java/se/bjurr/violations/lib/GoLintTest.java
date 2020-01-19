package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.GOLINT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class GoLintTest {

  @Test
  public void testThatGoLintViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/golint/golint\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(GOLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(7);

    Violation violation = actual.get(6);
    assertThat(violation.getMessage()) //
        .isEqualTo(
            "comment on exported type RestDataSource should be of the form \"RestDataSource ...\" (with optional leading article)");
    assertThat(violation.getFile()) //
        .isEqualTo("src/bla/bla/bla/dataSource.go");
    assertThat(violation.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(violation.getRule()) //
        .isEqualTo("");
    assertThat(violation.getStartLine()) //
        .isEqualTo(28);
    assertThat(violation.getEndLine()) //
        .isEqualTo(28);

    Violation violation2 = actual.get(1);
    assertThat(violation2.getMessage()) //
        .isEqualTo("declaration of err shadows declaration at journalevent.go:165: (vet shadow)  ");
    assertThat(violation2.getFile()) //
        .isEqualTo("journalevent.go");
    assertThat(violation2.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation2.getRule()) //
        .isEqualTo("");
    assertThat(violation2.getStartLine()) //
        .isEqualTo(182);
    assertThat(violation2.getEndLine()) //
        .isEqualTo(182);
  }

  @Test
  public void testThatGoVetViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/golint/govet\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(GOLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    assertThat(actual.get(0).getMessage()) //
        .isEqualTo("this is a message");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("my_file.go");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(INFO);
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(46);
  }
}
