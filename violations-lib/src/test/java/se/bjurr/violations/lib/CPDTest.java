package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
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
            .withPattern(".*/cpd/.*\\.xml$") //
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
}
