package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Reporter.KLOCWORK;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class KlocworkTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsReporterApi() //
            .withPattern(".*/klocwork/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(KLOCWORK) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    Violation violation0 = actual.get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("In method main. Variable 'bzz' was never read after null being assigned.");
    assertThat(violation0.getFile()) //
        .isEqualTo("/home/test_build/src/main/java/Main.java");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(violation0.getRule().get()) //
        .isEqualTo("JD.VNU.NULL");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(1);

    Violation violation1 = actual.get(1);
    assertThat(violation1.getMessage()) //
        .isEqualTo(
            "In method getURLConnection. The 'getURLConnection' method throws a generic exception 'java.lang.Exception'");
    assertThat(violation1.getFile()) //
        .isEqualTo("/home/test_build/src/main/java/Main2.java");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(violation0.getRule().get()) //
        .isEqualTo("JD.VNU.NULL");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(1);
  }
}
