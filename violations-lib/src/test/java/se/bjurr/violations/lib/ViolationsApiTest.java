package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.reports.Parser.CHECKSTYLE;

import java.util.Arrays;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class ViolationsApiTest {
  private final String rootFolder = getRootFolder();

  @Test
  public void testThatPathsCanBeIgnored() {
    final Set<Violation> actualNotIgnored =
        violationsApi()
            .withPattern(".*/mixed/.*")
            .inFolder(this.rootFolder)
            .findAll(CHECKSTYLE)
            .violations();
    assertThat(actualNotIgnored).hasSize(8);

    final Set<Violation> actualIgnoredCheckstyle =
        violationsApi()
            .withPattern(".*/mixed/.*")
            .inFolder(this.rootFolder)
            .withIgnorePaths(Arrays.asList("mixed/checkstyle"))
            .findAll(CHECKSTYLE)
            .violations();
    assertThat(actualIgnoredCheckstyle).hasSize(4);

    final Set<Violation> actualIgnoredMixed =
        violationsApi()
            .withPattern(".*/mixed/.*")
            .inFolder(this.rootFolder)
            .withIgnorePaths(Arrays.asList("mixed"))
            .findAll(CHECKSTYLE)
            .violations();
    assertThat(actualIgnoredMixed).hasSize(0);
  }
}
