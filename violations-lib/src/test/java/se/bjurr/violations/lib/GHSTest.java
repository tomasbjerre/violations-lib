package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.GHS;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class GHSTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/ghs/output.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(GHS) //
            .violations();

    assertThat(actual).hasSize(2);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("overloading the &&, ||, or comma operators is dangerous");
    assertThat(violation0.getFile()) //
        .isEqualTo("FormattedDate.cpp");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(91);

    final Violation violation1 = new ArrayList<>(actual).get(1);
    assertThat(violation1.getMessage()) //
        .isEqualTo("return value type does not match the function type");
    assertThat(violation1.getFile()) //
        .isEqualTo("profile.cpp");
    assertThat(violation1.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation1.getRule()) //
        .isEqualTo("");
    assertThat(violation1.getStartLine()) //
        .isEqualTo(89);
  }
}
