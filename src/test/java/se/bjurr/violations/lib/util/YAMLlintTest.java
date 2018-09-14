package se.bjurr.violations.lib.util;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.YAMLLINT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class YAMLlintTest {
    @Test
    public void testThatViolationsCanBeParsed() {
        final String rootFolder = getRootFolder();

        final List<Violation> actual =
                violationsApi() //
                        .withPattern(".*/yamllint/.*\\.txt$") //
                        .inFolder(rootFolder) //
                        .findAll(YAMLLINT) //
                        .violations();

        assertThat(actual) //
                .contains( //
                        violationBuilder() //
                                .setParser(YAMLLINT) //
                                .setFile("my_file.yml") //
                                .setStartLine(7) //
                                .setEndLine(7) //
                                .setMessage("line too long (115 > 88 characters)") //
                                .setRule("line-length") //
                                .setSeverity(ERROR) //
                                .build() //
                ) //
                .hasSize(17);

        assertThat(actual.get(1).getMessage()) //
                .isEqualTo("missing starting space in comment (comments)");
        assertThat(actual.get(1).getFile()) //
                .isEqualTo("file.yml");
        assertThat(actual.get(1).getSeverity()) //
                .isEqualTo(WARN);
        assertThat(actual.get(1).getRule()) //
                .isEqualTo("comments");

        assertThat(actual.get(2).getMessage()) //
                .isEqualTo("trailing spaces");
        assertThat(actual.get(2).getFile()) //
                .isEqualTo("test/file.yml");
        assertThat(actual.get(2).getSeverity()) //
                .isEqualTo(ERROR);
    }
}
