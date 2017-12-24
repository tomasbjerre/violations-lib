package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.filterRule;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.parsers.FindbugsParser.FINDBUGS_SPECIFIC_RANK;
import static se.bjurr.violations.lib.reports.Parser.FINDBUGS;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class FindbugsTest {

  private List<Violation> actual;

  @Before
  public void before() {
    String rootFolder = getRootFolder();
    actual =
        violationsApi() //
            .withPattern(".*/findbugs/main\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(FINDBUGS) //
            .violations();

    assertThat(actual) //
        .hasSize(5);
  }

  @Test
  public void testMavenGeneratedFindbugs() {
    String rootFolder = getRootFolder();
    List<Violation> maven =
        violationsApi() //
            .withPattern(".*/findbugs/fromMaven.xml$") //
            .inFolder(rootFolder) //
            .findAll(FINDBUGS) //
            .violations();

    assertThat(maven) //
        .hasSize(1);
  }

  @Test
  public void testThatEqualsUseHashCodeCanBeParsed() {
    Iterable<Violation> equalsUseHashCode = filterRule(actual, "HE_EQUALS_USE_HASHCODE");
    assertThat(equalsUseHashCode) //
        .hasSize(1);
  }

  @Test
  public void testThatNamingConventionCanBeParsed() {
    Iterable<Violation> equalsUseHashCode = filterRule(actual, "NM_FIELD_NAMING_CONVENTION");
    assertThat(equalsUseHashCode) //
        .hasSize(1);
  }

  @Test
  public void testThatViolationsCanBeParsed() {
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("se/bjurr/violations/lib/example/MyClass.java");
    assertThat(actual.get(0).getMessage()) //
        .startsWith("equals method always returns true") //
        .doesNotContain("CDATA");
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(17);
    assertThat(actual.get(0).getEndLine()) //
        .isEqualTo(17);
    assertThat(actual.get(0).getRule().get()) //
        .isEqualTo("EQ_ALWAYS_TRUE");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actual.get(0).getSource().get()) //
        .isEqualTo("se.bjurr.violations.lib.example.MyClass");
    assertThat(actual.get(0).getSpecifics().get(FINDBUGS_SPECIFIC_RANK)) //
        .isEqualTo("7");
  }
}
