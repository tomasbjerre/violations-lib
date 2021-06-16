package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.*;
import static se.bjurr.violations.lib.reports.Parser.MSBULDLOG;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class MSBuildLogTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/msbuildlog/msbuild\\.log$") //
            .inFolder(rootFolder) //
            .findAll(MSBULDLOG) //
            .violations();

    assertThat(actual) //
        .hasSize(15);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("A closing brace should not be preceded by a blank line.");
    assertThat(violation0.getFile()) //
        .isEqualTo("C:/Users/kaiser/source/repos/ConsoleApp1/ConsoleApp1/Program.cs");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("SA1508");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(16);

    final Violation violation3 = new ArrayList<>(actual).get(11);
    assertThat(violation3.getMessage()) //
        .isEqualTo("Element 'Main' should declare an access modifier");
    assertThat(violation3.getFile()) //
        .isEqualTo("C:/Users/kaiser/source/repos/ConsoleApp1/ConsoleApp1/Program.cs");
    assertThat(violation3.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation3.getRule()) //
        .isEqualTo("SA1400");
    assertThat(violation3.getStartLine()) //
        .isEqualTo(7);
  }
}
