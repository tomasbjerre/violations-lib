package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.DOCFX;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class DocFXTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/docfx/.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(DOCFX) //
            .violations();

    assertThat(actual) //
        .hasSize(3);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("Invalid file link:(~/mobiilirajapinta/puuttuu.md).");
    assertThat(violation0.getFile()) //
        .isEqualTo("mobiilirajapinta/json-dateandtime.md");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("InvalidFileLink");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(18);

    final Violation violation1 = new ArrayList<>(actual).get(0);
    assertThat(violation1.getFile()) //
        .isEqualTo("mobiilirajapinta/json-dateandtime.md");
    assertThat(violation1.getMessage()) //
        .isEqualTo("Invalid file link:(~/mobiilirajapinta/puuttuu.md).");
    assertThat(violation1.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation1.getRule()) //
        .isEqualTo("InvalidFileLink");
    assertThat(violation1.getStartLine()) //
        .isEqualTo(18);

    final Violation violation2 = new ArrayList<>(actual).get(2);
    assertThat(violation2.getMessage()) //
        .isEqualTo("Invalid file link:(~/missing.md#mobiilisovellus).");
    assertThat(violation2.getFile()) //
        .isEqualTo("sanasto.md");
    assertThat(violation2.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation2.getRule()) //
        .isEqualTo("InvalidFileLink");
    assertThat(violation2.getStartLine()) //
        .isEqualTo(63);
  }
}
