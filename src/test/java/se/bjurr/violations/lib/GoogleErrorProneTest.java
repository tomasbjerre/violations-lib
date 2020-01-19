package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.GOOGLEERRORPRONE;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class GoogleErrorProneTest {

  @Test
  public void testThatViolationsCanBeParsedGradle() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/googleErrorProne/googleErrorProne\\.log$") //
            .inFolder(rootFolder) //
            .findAll(GOOGLEERRORPRONE) //
            .violations();

    assertThat(actual) //
        .hasSize(5);

    final Violation violation0 = actual.get(0);
    assertThat(violation0.getMessage()) //
        .endsWith("Splitter.on(\",\").split(link)) {'?");
    assertThat(violation0.getFile()) //
        .isEqualTo(
            "/home/bjerre/workspace/git-changelog/git-changelog-lib/src/main/java/se/bjurr/gitchangelog/internal/integrations/github/GitHubHelper.java");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("StringSplitter");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(51);

    final Violation violation4 = actual.get(3);
    assertThat(violation4.getMessage()) //
        .endsWith(", otherCommitTime);'?");
    assertThat(violation4.getFile()) //
        .isEqualTo(
            "home/bjerre/workspace/git-changelog/git-changelog-lib/src/main/java/se/bjurr/gitchangelog/internal/git/TraversalWork.java");
    assertThat(violation4.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation4.getRule()) //
        .isEqualTo("BoxedPrimitiveConstructor");
    assertThat(violation4.getStartLine()) //
        .isEqualTo(73);
  }

  @Test
  public void testThatViolationsCanBeParsedMaven() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/googleErrorProne/googleErrorProneMaven\\.log$") //
            .inFolder(rootFolder) //
            .findAll(GOOGLEERRORPRONE) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation violation0 = actual.get(0);
    assertThat(violation0.getMessage()) //
        .endsWith("row new Exception();'?");
    assertThat(violation0.getFile()) //
        .isEqualTo("../examples/maven/error_prone_should_flag/src/main/java/Main.java");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation0.getRule()) //
        .isEqualTo("DeadException");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(20);
  }

  @Test
  public void testThatViolationsCanBeParsedGradleNullAway() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/googleErrorProne/nullAway\\.log$") //
            .inFolder(rootFolder) //
            .findAll(GOOGLEERRORPRONE) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violation0 = actual.get(0);
    assertThat(violation0.getMessage()) //
        .endsWith("nullaway )");
    assertThat(violation0.getFile()) //
        .isEqualTo(
            "home/travis/build/leinardi/FloatingActionButtonSpeedDial/library/src/main/java/com/leinardi/android/speeddial/SpeedDialActionItem.java");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation0.getRule()) //
        .isEqualTo("NullAway");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(175);
  }
}
