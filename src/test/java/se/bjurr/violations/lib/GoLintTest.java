package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Reporter.GOLINT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class GoLintTest {

  @Test
  public void testThatGoLintViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsReporterApi() //
            .withPattern(".*/golint/golint\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(GOLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(7);

    assertThat(actual.get(0).getMessage()) //
        .isEqualTo(
            "comment on exported type RestDataSource should be of the form \"RestDataSource ...\" (with optional leading article)");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("src/bla/bla/bla/dataSource.go");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(INFO);
    assertThat(actual.get(0).getRule().orNull()) //
        .isEqualTo(null);
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(28);
    assertThat(actual.get(0).getEndLine()) //
        .isEqualTo(28);

    assertThat(actual.get(2).getMessage()) //
        .isEqualTo("declaration of err shadows declaration at journalevent.go:165: (vet shadow)  ");
    assertThat(actual.get(2).getFile()) //
        .isEqualTo("journalevent.go");
    assertThat(actual.get(2).getSeverity()) //
        .isEqualTo(WARN);
    assertThat(actual.get(2).getRule().orNull()) //
        .isEqualTo(null);
    assertThat(actual.get(2).getStartLine()) //
        .isEqualTo(182);
    assertThat(actual.get(2).getEndLine()) //
        .isEqualTo(182);
  }

  @Test
  public void testThatGoVetViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsReporterApi() //
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
