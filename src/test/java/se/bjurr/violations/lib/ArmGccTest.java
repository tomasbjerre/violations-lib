package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.CLANG;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class ArmGccTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/arm-gcc/output.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(CLANG) //
            .violations();

    assertThat(actual) //
        .hasSize(4);

    final Violation violation0 = actual.get(2);
    assertThat(violation0.getMessage()) //
        .isEqualTo("comparison between signed and unsigned integer expressions [-Wsign-compare]");
    assertThat(violation0.getFile()) //
        .isEqualTo("../../external/specific/arm/cmsis/arm_math.h");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(5774);
  }
}
