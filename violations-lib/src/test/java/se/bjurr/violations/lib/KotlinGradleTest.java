package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.KOTLINGRADLE;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class KotlinGradleTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/kotlingradle/kotlin-gradle-example.*\\.txt") //
            .inFolder(rootFolder) //
            .findAll(KOTLINGRADLE) //
            .violations();

    assertThat(actual) //
        .hasSize(4);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo(
            "Elvis operator (?:) always returns the left operand of non-nullable type String");
    assertThat(violation0.getFile()) //
        .isEqualTo("/Users/scottkennedy/project/src/main/java/com/example/Test.kt");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("");
    assertThat(violation0.getParser()) //
        .isEqualTo(KOTLINGRADLE);
    assertThat(violation0.getColumn()).isEqualTo(87);

    final Violation violation3 = new ArrayList<>(actual).get(3);
    assertThat(violation3.getFile())
        .isEqualTo(
            "C:/Users/User/Documents/testProject/src/main/java/ru/novikov/maps/CameraPosition.kt");
    assertThat(violation3.getStartLine()).isEqualTo(25);
  }

  @Test
  public void testThatViolationsCanBeParsedFrom_Gradle_7_6() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/kotlingradle/kotlin-gradle7_6-example.*\\.txt") //
            .inFolder(rootFolder) //
            .findAll(KOTLINGRADLE) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("Unresolved reference: SampleModelEntity");
    assertThat(violation0.getFile()) //
        .isEqualTo(
            "file:///builds/.../common-data/src/test/kotlin/.../common/data/sample/DatabaseSampleRepositoryTest.kt");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(SEVERITY.ERROR);
    assertThat(violation0.getRule()) //
        .isEqualTo("");
    assertThat(violation0.getParser()) //
        .isEqualTo(KOTLINGRADLE);
    assertThat(violation0.getStartLine()).isEqualTo(37);
    assertThat(violation0.getColumn()).isEqualTo(50);

    final Violation violation1 = new ArrayList<>(actual).get(1);
    assertThat(violation1.getMessage()) //
        .isEqualTo("Parameter 'uiEvents' is never used");
    assertThat(violation1.getFile()) //
        .isEqualTo(
            "file:///builds/.../feature-cards/src/main/kotlin/.../feature/cards/overview/CardOverviewScreen.kt");
    assertThat(violation1.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation1.getRule()) //
        .isEqualTo("");
    assertThat(violation1.getParser()) //
        .isEqualTo(KOTLINGRADLE);
    assertThat(violation1.getStartLine()).isEqualTo(36);
    assertThat(violation1.getColumn()).isEqualTo(5);
  }
}
