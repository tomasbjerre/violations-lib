package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.CLANG;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class DoxygenTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/doxygen/output.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(CLANG) //
            .violations();

    assertThat(actual) //
        .hasSize(3);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo(
            "argument 'index' of command @param is not found in the argument list of arm_min_q7(q7_t *pSrc, uint32_t blockSize, q7_t *pResult, uint32_t *pIndex)");
    assertThat(violation0.getFile()) //
        .isEqualTo("./pump/external/specific/arm/cmsis_dsp/StatisticsFunctions/arm_min_q7.c");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(48);
  }
}
