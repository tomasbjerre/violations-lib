package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.CLANG;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class CLangTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/clang/clang.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(CLANG) //
            .violations();

    assertThat(actual) //
        .hasSize(4);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("'test.h' file not found");
    assertThat(violation0.getFile()) //
        .isEqualTo("./test.h");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation0.getRule()) //
        .isEqualTo("");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(10);

    final Violation violation3 = new ArrayList<>(actual).get(3);
    assertThat(violation3.getMessage()) //
        .isEqualTo("Memory is allocated");
    assertThat(violation3.getFile()) //
        .isEqualTo("main.cpp");
    assertThat(violation3.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(violation3.getRule()) //
        .isEqualTo("");
    assertThat(violation3.getStartLine()) //
        .isEqualTo(4);
  }

  @Test
  public void testThatRubycopViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/clang/rubycop\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(CLANG) //
            .violations();

    assertThat(actual) //
        .hasSize(4);

    final Violation violation0 = new ArrayList<>(actual).get(3);
    assertThat(violation0.getMessage()) //
        .isEqualTo("Use snake_case for method names.");
    assertThat(violation0.getFile()) //
        .isEqualTo("test.rb");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation0.getRule()) //
        .isEqualTo("");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(1);

    assertThat(new ArrayList<>(actual).get(0).getSeverity()) //
        .isEqualTo(WARN);
  }
}
