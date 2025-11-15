package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.GOOGLEERRORPRONE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class GoogleErrorProneTest {

  @Test
  public void testThatViolationsCanBeParsedGradle() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/googleErrorProne/googleErrorProne\\.log$") //
            .inFolder(rootFolder) //
            .findAll(GOOGLEERRORPRONE) //
            .violations();

    assertThat(actual) //
        .hasSize(5);

    final Violation violation0 =
        findViolation(
            actual, //
            "/home/bjerre/workspace/git-changelog/git-changelog-lib/src/main/java/se/bjurr/gitchangelog/internal/integrations/github/GitHubHelper.java", //
            51);
    assertThat(violation0.getMessage()) //
        .endsWith("Splitter.on(\",\").split(link)) {'?");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("StringSplitter");

    final Violation violation4 =
        findViolation(
            actual, //
            "/home/bjerre/workspace/git-changelog/git-changelog-lib/src/main/java/se/bjurr/gitchangelog/internal/git/TraversalWork.java", //
            73);
    assertThat(violation4.getMessage()) //
        .endsWith(", otherCommitTime);'?");
    assertThat(violation4.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation4.getRule()) //
        .isEqualTo("BoxedPrimitiveConstructor");
  }

  /**
   * Test fix for #153: When running ErrorProne from Gradle on Windows, errors are reported for
   * absolute paths (e.g. {@code C:\\a\\b\\c.txt}).
   */
  @Test
  public void testThatAbsoluteWindowsPathsAreRecognised() {
    final Set<Violation> violations =
        violationsApi() //
            .withPattern(".*/googleErrorProne/errorprone-windows\\.log$") //
            .inFolder(getRootFolder()) //
            .findAll(GOOGLEERRORPRONE) //
            .violations();

    assertThat(violations) //
        .hasSize(1);

    Violation v = violations.iterator().next();
    assertThat(v.getMessage()) //
        .startsWith("returning @Nullable expression from method with @NonNull return type");
    // Constructor of 'Violation' replaces all backslashes with forward slashes
    assertThat(v.getFile()) //
        .isEqualTo("C:/proj/src/main/java/Test.java");
    assertThat(v.getStartLine()) //
        .isEqualTo(12);
  }

  @Test
  public void testThatViolationsCanBeParsedMaven() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/googleErrorProne/googleErrorProneMaven\\.log$") //
            .inFolder(rootFolder) //
            .findAll(GOOGLEERRORPRONE) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .endsWith("row new Exception();'?");
    assertThat(violation0.getFile()) //
        .isEqualTo(".../examples/maven/error_prone_should_flag/src/main/java/Main.java");
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

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/googleErrorProne/nullAway\\.log$") //
            .inFolder(rootFolder) //
            .findAll(GOOGLEERRORPRONE) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .endsWith("nullaway )");
    assertThat(violation0.getFile()) //
        .isEqualTo(
            "/home/travis/build/leinardi/FloatingActionButtonSpeedDial/library/src/main/java/com/leinardi/android/speeddial/SpeedDialActionItem.java");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation0.getRule()) //
        .isEqualTo("NullAway");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(175);
  }

  private static Violation findViolation(Collection<Violation> violations, String file, int line) {
    return violations.stream() //
        .filter(v -> v.getFile().equals(file) && v.getStartLine() == line) //
        .findFirst() //
        .orElseThrow(() -> new AssertionError("Violation not found: " + file + ":" + line));
  }
}
