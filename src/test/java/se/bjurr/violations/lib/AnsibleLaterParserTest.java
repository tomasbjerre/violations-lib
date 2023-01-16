package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.reports.Parser.ANSIBLELATER;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class AnsibleLaterParserTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/ansiblelater/ansible-later.*\\.json$") //
            .inFolder(rootFolder) //
            .findAll(ANSIBLELATER) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo(
            "[ANSIBLE0004] Standard 'YAML should use consistent number of spaces around variables' not met: simple_role.yml:7: no suitable numbers of spaces (min: 1 max: 1)");
    assertThat(violation0.getFile()) //
        .isEqualTo("simple_role.yml");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(SEVERITY.ERROR);
    assertThat(violation0.getRule()) //
        .isEqualTo("ANSIBLE0004");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(7);
  }
}
