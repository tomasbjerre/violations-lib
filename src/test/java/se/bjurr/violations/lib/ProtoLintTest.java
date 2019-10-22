package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.PROTOLINT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class ProtoLintTest {

  @Test
  public void testThatViolationsCanBeParsed() {

    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/protolint/protolint\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(PROTOLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(2591);

    assertThat(actual.get(0)) //
        .isEqualTo( //
            violationBuilder() //
                .setParser(PROTOLINT) //
                .setFile("google/ads/googleads/v1/common/ad_asset.proto") //
                .setStartLine(25) //
                .setColumn(1) //
                .setMessage("The line length is 91, but it must be shorter than 80") //
                .setSeverity(ERROR) //
                .build() //
            );

    assertThat(actual.get(1)) //
        .isEqualTo( //
            violationBuilder() //
                .setParser(PROTOLINT) //
                .setFile("google/ads/googleads/v1/common/ad_type_infos.proto") //
                .setStartLine(30) //
                .setColumn(1) //
                .setMessage("The line length is 91, but it must be shorter than 80") //
                .setSeverity(ERROR) //
                .build() //
            );

    assertThat(actual.get(403)) //
        .isEqualTo( //
            violationBuilder() //
                .setParser(PROTOLINT) //
                .setFile("google/ads/googleads/v1/resources/campaign.proto") //
                .setStartLine(41) //
                .setColumn(1) //
                .setMessage("Imports are not sorted.") //
                .setSeverity(ERROR) //
                .build() //
            );
  }
}
