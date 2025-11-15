package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.CLANG;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class GccTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/gcc/output.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(CLANG) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("comparison between signed and unsigned integer expressions [-Wsign-compare]");
    assertThat(violation0.getFile()) //
        .isEqualTo("../../../pump/source/util/FormattedDate.cpp");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(82);

    final Violation violation1 = new ArrayList<>(actual).get(1);
    assertThat(violation1.getMessage()) //
        .isEqualTo("variable 'exceedingDuration' set but not used [-Wunused-but-set-variable]");
    assertThat(violation1.getFile()) //
        .isEqualTo("../../../pump/source/util/profile/profile_function_overlay.cpp");
    assertThat(violation1.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation1.getRule()) //
        .isEqualTo("");
    assertThat(violation1.getStartLine()) //
        .isEqualTo(112);
  }
}
