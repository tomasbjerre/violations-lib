package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CPPCHECK;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;

public class CPPCheckTest {

  private static final String MSG_1 =
      "The scope of the variable 'n' can be reduced. Warning: It can be unsafe to fix this message. Be careful. Especially when there are inner loops. Here is an example where cppcheck will write that the scope for 'i' can be reduced:&#xa;void f(int x)&#xa;{&#xa;    int i = 0;&#xa;    if (x) {&#xa;        // it's safe to move 'int i = 0' here&#xa;        for (int n = 0; n < 10; ++n) {&#xa;            // it is possible but not safe to move 'int i = 0' here&#xa;            do_something(&i);&#xa;        }&#xa;    }&#xa;}&#xa;When you see this message it is always safe to reduce the variable scope 1 level.";
  private static final String MSG_2 =
      "The scope of the variable 'i' can be reduced. Warning: It can be unsafe to fix this message. Be careful. Especially when there are inner loops. Here is an example where cppcheck will write that the scope for 'i' can be reduced:&#xa;void f(int x)&#xa;{&#xa;    int i = 0;&#xa;    if (x) {&#xa;        // it's safe to move 'int i = 0' here&#xa;        for (int n = 0; n < 10; ++n) {&#xa;            // it is possible but not safe to move 'int i = 0' here&#xa;            do_something(&i);&#xa;        }&#xa;    }&#xa;}&#xa;When you see this message it is always safe to reduce the variable scope 1 level.";

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/cppcheck/main\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    assertThat(actual) //
        .contains( //
            violationBuilder() //
                .setParser(CPPCHECK) //
                .setFile("api.c") //
                .setStartLine(498) //
                .setEndLine(498) //
                .setRule("variableScope") //
                .setMessage(MSG_1) //
                .setSeverity(INFO) //
                .setGroup("1") //
                .build()) //
        .contains( //
            violationBuilder() //
                .setParser(CPPCHECK) //
                .setFile("api_storage.c") //
                .setStartLine(104) //
                .setEndLine(104) //
                .setRule("variableScope") //
                .setMessage(MSG_2) //
                .setSeverity(ERROR) //
                .setGroup("2") //
                .build()) //
        .hasSize(3);
  }

  @Test
  public void testThatViolationsCanBeParsedExample1() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/cppcheck/example1\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    final Violation violation0 = actual.get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("Variable 'it' is reassigned a value before the old one has been used.");

    final Violation violation1 = actual.get(1);
    assertThat(violation1.getMessage()) //
        .isEqualTo("Variable 'it' is reassigned a value before the old one has been used.");

    final Violation violation2 = actual.get(2);
    assertThat(violation2.getMessage()) //
        .isEqualTo("Condition 'rc' is always true");

    final Violation violation3 = actual.get(3);
    assertThat(violation3.getMessage()) //
        .isEqualTo("Condition 'rc' is always true. Assignment 'rc=true', assigned value is 1");
  }

  @Test
  public void testThatViolationsCanBeParsedByRule() {
    final String rootFolder = getRootFolder();

    final List<Violation> violationsList =
        violationsApi() //
            .withPattern(".*/cppcheck/example1\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    final Map<String, List<Violation>> violationsPerRule =
        violationsList
            .stream() //
            .collect(Collectors.groupingBy(Violation::getRule));
    assertThat(violationsPerRule) //
        .hasSize(2);
    assertThat(violationsPerRule.get("redundantAssignment")) //
        .hasSize(2);

    final Map<Parser, List<Violation>> violationsPerParser =
        violationsList
            .stream() //
            .collect(Collectors.groupingBy(Violation::getParser));
    assertThat(violationsPerParser) //
        .hasSize(1);
    assertThat(violationsPerParser.get(CPPCHECK)) //
        .hasSize(4);

    final Map<String, List<Violation>> violationsPerGroupMap =
        violationsList
            .stream() //
            .collect(Collectors.groupingBy(Violation::getGroup));
    assertThat(violationsPerGroupMap) //
        .as("There are 2 error tags and there should be 2 groups identified.") //
        .hasSize(2);

    final List<Violation> firstErrorTag = violationsPerGroupMap.get("0");
    assertThat(firstErrorTag) //
        .hasSize(2);
    assertThat(firstErrorTag.get(0).getMessage()) //
        .isEqualTo("Variable 'it' is reassigned a value before the old one has been used.");

    final List<Violation> secondErrorTag = violationsPerGroupMap.get("1");
    assertThat(secondErrorTag) //
        .hasSize(2);
    assertThat(secondErrorTag.get(0).getMessage()) //
        .isEqualTo("Condition 'rc' is always true");
  }

  @Test
  public void testThatViolationsCanBeParsedWhenErrorTagHasNoEndtag() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/cppcheck/error_without_endtag\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    final Violation violation0 = actual.get(0);
    assertThat(violation0.getMessage()) //
        .startsWith("The scope of the variable");
  }

  @Test
  public void testThatViolationsCanBeParsedWithVersion2() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/cppcheck/results-version-2\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    assertThat(actual) //
        .hasSize(4);

    final Violation violation0 = actual.get(0);
    assertThat(violation0.getStartLine()) //
        .isEqualTo(53);
    assertThat(violation0.getMessage()) //
        .isEqualTo("Variable 'it' is reassigned a value before the old one has been used.");

    final Violation violation1 = actual.get(1);
    assertThat(violation1.getStartLine()) //
        .isEqualTo(51);
    assertThat(violation1.getMessage()) //
        .isEqualTo("Variable 'it' is reassigned a value before the old one has been used.");

    final Violation violation2 = actual.get(2);
    assertThat(violation2.getStartLine()) //
        .isEqualTo(53);
    assertThat(violation2.getMessage()) //
        .isEqualTo("Variable 'that' is reassigned a value before the old one has been used.");

    final Violation violation3 = actual.get(3);
    assertThat(violation3.getStartLine()) //
        .isEqualTo(51);
    assertThat(violation3.getMessage()) //
        .isEqualTo("Variable 'that' is reassigned a value before the old one has been used.");
  }

  @Test
  public void testThatEmptyReportCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/cppcheck/empty\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    assertThat(actual) //
        .hasSize(0);
  }
}
