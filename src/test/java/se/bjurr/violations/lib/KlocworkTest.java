package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Parser.KLOCWORK;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class KlocworkTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/klocwork/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(KLOCWORK) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violation0 = actual.get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo(
            "In method main. Variable 'bzz' was never read after null being assigned. http://server:8080/review/insight-review.html#goto:project=TestProject,pid=10");
    assertThat(violation0.getFile()) //
        .isEqualTo("/home/test_build/src/main/java/Main.java");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(violation0.getRule()) //
        .isEqualTo("JD.VNU.NULL");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(1);

    final Violation violation1 = actual.get(1);
    assertThat(violation1.getMessage()) //
        .isEqualTo(
            "In method getURLConnection. The 'getURLConnection' method throws a generic exception 'java.lang.Exception' http://server:8080/review/insight-review.html#goto:project=TestProject,pid=15");
    assertThat(violation1.getFile()) //
        .isEqualTo("/home/test_build/src/main/java/Main2.java");
    assertThat(violation1.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(violation1.getRule()) //
        .isEqualTo("EXC.BROADTHROWS");
    assertThat(violation1.getStartLine()) //
        .isEqualTo(1);
  }
}
