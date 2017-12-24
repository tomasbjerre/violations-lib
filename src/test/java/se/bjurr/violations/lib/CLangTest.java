package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.CLANG;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class CLangTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsApi() //
            .withPattern(".*/clang/clang.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(CLANG) //
            .violations();

    assertThat(actual) //
        .hasSize(4);

    assertThat(actual.get(0).getMessage()) //
        .isEqualTo("'test.h' file not found");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("./test.h");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actual.get(0).getRule().orNull()) //
        .isEqualTo(null);
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(10);

    assertThat(actual.get(2).getMessage()) //
        .isEqualTo("Memory is allocated");
    assertThat(actual.get(2).getFile()) //
        .isEqualTo("main.cpp");
    assertThat(actual.get(2).getSeverity()) //
        .isEqualTo(INFO);
    assertThat(actual.get(2).getRule().orNull()) //
        .isEqualTo(null);
    assertThat(actual.get(2).getStartLine()) //
        .isEqualTo(4);
  }

  @Test
  public void testThatRubycopViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsApi() //
            .withPattern(".*/clang/rubycop\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(CLANG) //
            .violations();

    assertThat(actual) //
        .hasSize(4);

    assertThat(actual.get(0).getMessage()) //
        .isEqualTo("Use snake_case for method names.");
    assertThat(actual.get(0).getFile()) //
        .isEqualTo("test.rb");
    assertThat(actual.get(0).getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(actual.get(0).getRule().orNull()) //
        .isEqualTo(null);
    assertThat(actual.get(0).getStartLine()) //
        .isEqualTo(1);

    assertThat(actual.get(3).getSeverity()) //
        .isEqualTo(WARN);
  }
}
