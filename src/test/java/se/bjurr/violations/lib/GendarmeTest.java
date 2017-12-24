package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Parser.GENDARME;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class GendarmeTest {
  @Test
  public void testThatViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsApi() //
            .withPattern(".*/gendarme/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(GENDARME) //
            .violations();

    assertThat(actual) //
        .hasSize(28);

    assertThat(actual.get(0).getMessage()) //
        .startsWith("This me");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("c:/Dev/src/hudson/Hudson.Domain/Dog.cs");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(INFO);
    assertThat(actual.get(0).getRule().get()) //
        .isEqualTo("MethodCanBeMadeStaticRule");
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(10);
    assertThat(actual.get(0).getEndLine()) //
        .isEqualTo(10);

    assertThat(actual.get(5).getMessage()) //
        .startsWith("A constructor ca");
  }

  @Test
  public void testThatViolationsCanBeParsedGendarmeUnix() {
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsApi() //
            .withPattern(".*/gendarme/Gendarme_unix\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(GENDARME) //
            .violations();

    assertThat(actual) //
        .hasSize(2);
  }
}
