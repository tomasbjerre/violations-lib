package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.KOTLINMAVEN;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class KotlinMavenTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/kotlinmaven/.*\\.txt") //
            .inFolder(rootFolder) //
            .findAll(KOTLINMAVEN) //
            .violations();

    assertThat(actual) //
        .hasSize(29);

    final Violation violation0 = actual.get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("Unchecked cast: AssetDao<*> to DaoOperations.Read<T, UUID>");
    assertThat(violation0.getFile()) //
        .isEqualTo(
            "/home/bjerre/workspace/kerub/src/main/kotlin/com/github/kerubistan/kerub/data/hub/AnyAssetDaoImpl.kt");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("");
    assertThat(violation0.getParser()) //
        .isEqualTo(KOTLINMAVEN);
  }
}
