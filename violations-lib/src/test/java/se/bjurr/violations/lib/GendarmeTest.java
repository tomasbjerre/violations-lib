package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Parser.GENDARME;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class GendarmeTest {
  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/gendarme/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(GENDARME) //
            .violations();

    assertThat(actual) //
        .hasSize(13);

    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .startsWith("This me");
    assertThat(new ArrayList<>(actual).get(0).getFile()) //
        .isEqualTo("/Dev/src/hudson/Hudson.Domain/Dog.cs");
    assertThat(new ArrayList<>(actual).get(0).getSeverity()) //
        .isEqualTo(INFO);
    assertThat(new ArrayList<>(actual).get(0).getRule()) //
        .isEqualTo("MethodCanBeMadeStaticRule");
    assertThat(new ArrayList<>(actual).get(0).getStartLine()) //
        .isEqualTo(22);
    assertThat(new ArrayList<>(actual).get(0).getEndLine()) //
        .isEqualTo(22);

    assertThat(new ArrayList<>(actual).get(5).getMessage()) //
        .startsWith("This type");
  }

  @Test
  public void testThatViolationsCanBeParsedGendarmeUnix() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/gendarme/Gendarme_unix\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(GENDARME) //
            .violations();

    assertThat(actual) //
        .hasSize(2);
  }
}
