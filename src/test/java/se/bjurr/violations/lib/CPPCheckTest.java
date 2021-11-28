package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CPPCHECK;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;
import se.bjurr.violations.lib.util.ViolationAsserter;

public class CPPCheckTest {

  private static final String MSG_1 = "The scope of the variable 'n' can be reduced.";
  private static final String MSG_2 = "The scope of the variable 'i' can be reduced.";

  /**
   * cppcheck --quiet --enable=all --force --inline-suppr --xml --xml-version=2 . 2>
   * cppcheck-result.xml
   */
  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/cppcheck/main\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    assertThat(actual).hasSize(3);

    final Violation v1 =
        violationBuilder() //
            .setParser(CPPCHECK) //
            .setFile("api.c") //
            .setStartLine(498) //
            .setEndLine(498) //
            .setRule("variableScope") //
            .setMessage(MSG_1) //
            .setSeverity(INFO) //
            .setGroup("1") //
            .build();

    final Violation v2 =
        violationBuilder() //
            .setParser(CPPCHECK) //
            .setFile("api_storage.c") //
            .setStartLine(104) //
            .setEndLine(104) //
            .setRule("variableScope") //
            .setMessage(MSG_2) //
            .setSeverity(ERROR) //
            .setGroup("2") //
            .build();
    ViolationAsserter.assertThat(actual) //
        .contains(v2, 2) //
        .contains(v1, 1);
  }

  @Test
  public void testThatViolationsCanBeParsedExample1() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/cppcheck/example1\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("Variable 'it' is reassigned a value before the old one has been used.");

    final Violation violation1 = new ArrayList<>(actual).get(1);
    assertThat(violation1.getMessage()) //
        .isEqualTo("Variable 'it' is reassigned a value before the old one has been used.");

    final Violation violation2 = new ArrayList<>(actual).get(2);
    assertThat(violation2.getMessage()) //
        .isEqualTo("Condition 'rc' is always true");

    final Violation violation3 = new ArrayList<>(actual).get(3);
    assertThat(violation3.getMessage()) //
        .isEqualTo("Condition 'rc' is always true. Assignment 'rc=true', assigned value is 1");
  }

  @Test
  public void testThatViolationsCanBeParsedByRule() {
    final String rootFolder = getRootFolder();

    final Set<Violation> violationsList =
        violationsApi() //
            .withPattern(".*/cppcheck/example1\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    final Map<String, List<Violation>> violationsPerRule =
        violationsList.stream() //
            .collect(Collectors.groupingBy(Violation::getRule));
    assertThat(violationsPerRule) //
        .hasSize(2);
    assertThat(violationsPerRule.get("redundantAssignment")) //
        .hasSize(2);

    final Map<Parser, List<Violation>> violationsPerParser =
        violationsList.stream() //
            .collect(Collectors.groupingBy(Violation::getParser));
    assertThat(violationsPerParser) //
        .hasSize(1);
    assertThat(violationsPerParser.get(CPPCHECK)) //
        .hasSize(4);

    final Map<String, List<Violation>> violationsPerGroupMap =
        violationsList.stream() //
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

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/cppcheck/error_without_endtag\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .startsWith("The scope of the variable");
  }

  @Test
  public void testThatViolationsCanBeParsedWithVersion2() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/cppcheck/results-version-2\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    assertThat(actual) //
        .hasSize(4);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getStartLine()) //
        .isEqualTo(53);
    assertThat(violation0.getMessage()) //
        .isEqualTo("Variable 'it' is reassigned a value before the old one has been used.");

    final Violation violation1 = new ArrayList<>(actual).get(1);
    assertThat(violation1.getStartLine()) //
        .isEqualTo(51);
    assertThat(violation1.getMessage()) //
        .isEqualTo("Variable 'it' is reassigned a value before the old one has been used.");

    final Violation violation2 = new ArrayList<>(actual).get(2);
    assertThat(violation2.getStartLine()) //
        .isEqualTo(53);
    assertThat(violation2.getMessage()) //
        .isEqualTo("Variable 'that' is reassigned a value before the old one has been used.");

    final Violation violation3 = new ArrayList<>(actual).get(3);
    assertThat(violation3.getStartLine()) //
        .isEqualTo(51);
    assertThat(violation3.getMessage()) //
        .isEqualTo("Variable 'that' is reassigned a value before the old one has been used.");
  }

  @Test
  public void testSelfClosingErrorTagScoping() {

    final List<LogRecord> severeLogEvents = new ArrayList<>();
    final Handler logHandler =
        new Handler() {
          @Override
          public void publish(final LogRecord record) {
            if (Level.SEVERE == record.getLevel()) {
              severeLogEvents.add(record);
            }
          }

          @Override
          public void flush() {}

          @Override
          public void close() throws SecurityException {}
        };
    Logger.getLogger("").setLevel(Level.SEVERE);
    Logger.getLogger("").addHandler(logHandler);
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/cppcheck/self_closing_scope_limited.*") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    assertThat(severeLogEvents) //
        .hasSize(0);
    assertThat(actual) //
        .hasSize(1);

    Logger.getLogger("").removeHandler(logHandler);
  }

  @Test
  public void testThatViolationsCanBeParsedFromIssue64519() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*issue64519\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation violation = new ArrayList<>(actual).get(0);
    assertThat(violation.getMessage()) //
        .startsWith("Cppcheck cannot find all the include files");
    assertThat(violation.getFile()) //
        .isEqualTo(Violation.NO_FILE);
    assertThat(violation.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(violation.getRule()) //
        .isEqualTo("missingInclude");
    assertThat(violation.getStartLine()) //
        .isEqualTo(0);
    assertThat(violation.getEndLine()) //
        .isEqualTo(0);
  }

  @Test
  public void testThatColumnCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*issue-136\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    assertThat(actual) //
        .hasSize(3);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .startsWith("Returning pointer to local variable");
    assertThat(violation0.getFile()) //
        .isEqualTo("folderWithCPPfiles/19389.cpp");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(ERROR);
    assertThat(violation0.getRule()) //
        .isEqualTo("returnDanglingLifetime");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(4);
    assertThat(violation0.getEndLine()) //
        .isEqualTo(4);
    assertThat(violation0.getColumn()) //
        .isEqualTo(13);
    assertThat(violation0.getEndColumn()) //
        .isNull();

    final Violation violation1 = new ArrayList<>(actual).get(1);
    assertThat(violation1.getColumn()) //
        .isEqualTo(13);

    final Violation violation2 = new ArrayList<>(actual).get(2);
    assertThat(violation2.getColumn()) //
        .isEqualTo(11);
  }
}
