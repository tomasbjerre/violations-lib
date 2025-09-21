package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;

public class CoverityTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/coverity/example1\\.json$") //
            .inFolder(rootFolder) //
            .findAll(Parser.COVERITY) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo(
            "Bitwise-and ('&amp;') operation applied to zero always produces zero.\nThe expression's value is always zero; construct may indicate an inadvertent logic error.");
    assertThat(violation0.getFile()) //
        .isEqualTo("C:/Workspace/workspace/Build_jenkins_development/somefile.cs");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getCategory()) //
        .isEqualTo("Integer handling issues");
    assertThat(violation0.getRule()) //
        .isEqualTo("constant_expression_result/bit_and_with_zero");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(79);
  }
}
