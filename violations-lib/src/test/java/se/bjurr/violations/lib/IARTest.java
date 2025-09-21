package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.reports.Parser.IAR;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class IARTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/iar/example\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(IAR) //
            .violations();

    assertThat(actual) //
        .hasSize(7);

    final Violation violation0 = new ArrayList<>(actual).get(3);
    assertThat(violation0.getFile()) //
        .isEqualTo(
            "c:/jenkins/workspace/24-Test-Jenkins-WinTen-Extension/external/specific/cpp/iar_cxxabi.cpp");
    assertThat(violation0.getMessage()) //
        .isEqualTo("expression must be a pointer to a complete object type");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(432);
    assertThat(violation0.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("Pe852");

    final Violation violation3 = new ArrayList<>(actual).get(2);
    assertThat(violation3.getFile()) //
        .isEqualTo("C:/Repositories/source/dal/InterMcu/InterMcuTransport.cpp");
    assertThat(violation3.getMessage()) //
        .isEqualTo("expected a \")\"");
    assertThat(violation3.getSeverity()) //
        .isEqualTo(SEVERITY.ERROR);
    assertThat(violation3.getRule()) //
        .isEqualTo("Pe018");

    final Violation violation6 = new ArrayList<>(actual).get(6);
    assertThat(violation6.getFile()) //
        .isEqualTo("source/dal/InterMcu/InterMcuTransport.cpp");
    assertThat(violation6.getMessage()) //
        .isEqualTo("expected a \")\"");
    assertThat(violation3.getSeverity()) //
        .isEqualTo(SEVERITY.ERROR);
    assertThat(violation3.getRule()) //
        .isEqualTo("Pe018");
  }
}
