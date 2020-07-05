package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CHECKSTYLE;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class CheckstyleTest {
  private final String rootFolder = getRootFolder();

  @Test
  public void testThatViolationsWithXmlSpecialCharactersCanBeParsed() {
    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/checkstyle/special_chars\\.xml$") //
            .inFolder(this.rootFolder) //
            .findAll(CHECKSTYLE) //
            .violations();

    assertThat(actual)
        .containsOnly(
            violationBuilder()
                //
                .setParser(CHECKSTYLE)
                //
                .setFile("/src/main/java/se/bjurr/violations/lib/example/MyClass.java") //
                .setSource(null) //
                .setStartLine(11) //
                .setEndLine(11) //
                .setColumn(10) //
                .setMessage("\'Must have at least one statement.\'") //
                .setRule("com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck") //
                .setSeverity(INFO) //
                .build(), //
            violationBuilder()
                //
                .setParser(CHECKSTYLE)
                //
                .setFile("/src/main/java/se/bjurr/violations/lib/example/MyClass.java") //
                .setSource(null) //
                .setStartLine(12) //
                .setEndLine(12) //
                .setColumn(10) //
                .setMessage("Must have at least one \"statement\".") //
                .setRule("com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck") //
                .setSeverity(INFO) //
                .build(), //
            violationBuilder()
                //
                .setParser(CHECKSTYLE)
                //
                .setFile("/src/main/java/se/bjurr/violations/lib/example/MyClass.java") //
                .setSource(null) //
                .setStartLine(13) //
                .setEndLine(13) //
                .setColumn(10) //
                .setMessage("one is < two") //
                .setRule("one.should.be.greater.than.two") //
                .setSeverity(INFO) //
                .build(), //
            violationBuilder()
                //
                .setParser(CHECKSTYLE)
                //
                .setFile("/src/main/java/se/bjurr/violations/lib/example/MyClass.java") //
                .setSource(null) //
                .setStartLine(14) //
                .setEndLine(14) //
                .setColumn(10) //
                .setMessage("two is > one") //
                .setRule("two.should.be.less.than.one") //
                .setSeverity(INFO) //
                .build(), //
            violationBuilder()
                //
                .setParser(CHECKSTYLE)
                //
                .setFile("/src/main/java/se/bjurr/violations/lib/example/MyClass.java") //
                .setSource(null) //
                .setStartLine(15) //
                .setEndLine(15) //
                .setColumn(10) //
                .setMessage("one & one is two") //
                .setRule("one.and.one.should.be.three") //
                .setSeverity(INFO) //
                .build() //
            );
  }

  @Test
  public void testThatViolationsCanBeParsed() {
    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/checkstyle/main\\.xml$") //
            .inFolder(this.rootFolder) //
            .findAll(CHECKSTYLE) //
            .violations();

    assertThat(actual) //
        .containsOnly(
            //
            violationBuilder()
                //
                .setParser(CHECKSTYLE)
                //
                .setReporter(CHECKSTYLE.name())
                //
                .setFile("/src/main/java/se/bjurr/violations/lib/example/MyClass.java") //
                .setSource(null) //
                .setStartLine(0) //
                .setEndLine(0) //
                .setColumn(null) //
                .setMessage("Missing package-info.java file.") //
                .setRule("com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck") //
                .setSeverity(ERROR) //
                .build(), //
            violationBuilder()
                //
                .setParser(CHECKSTYLE)
                //
                .setFile("/src/main/java/se/bjurr/violations/lib/example/MyClass.java") //
                .setSource(null) //
                .setStartLine(9) //
                .setEndLine(9) //
                .setColumn(10) //
                .setMessage("Must have at least one statement.") //
                .setRule("com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck") //
                .setSeverity(INFO) //
                .build(), //
            violationBuilder()
                //
                .setParser(CHECKSTYLE)
                //
                .setFile("/src/main/java/se/bjurr/violations/lib/example/OtherClass.java") //
                .setSource(null) //
                .setStartLine(10) //
                .setEndLine(10) //
                .setColumn(31) //
                .setMessage("Must have at least one statement.") //
                .setRule("com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck") //
                .setSeverity(INFO) //
                .build(), //
            violationBuilder()
                //
                .setParser(CHECKSTYLE)
                //
                .setFile("/src/main/java/se/bjurr/violations/lib/example/OtherClass.java") //
                .setSource(null) //
                .setStartLine(26) //
                .setEndLine(26) //
                .setColumn(3) //
                .setMessage("Boolean expression complexity is 8 (max allowed is 1).") //
                .setRule(
                    "com.puppycrawl.tools.checkstyle.checks.metrics.BooleanExpressionComplexityCheck") //
                .setSeverity(WARN) //
                .build() //
            );
  }

  @Test
  public void testThatPHPViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/checkstyle/phpcheckstyle\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CHECKSTYLE) //
            .withReporter("PHP") //
            .violations();

    assertThat(actual) //
        .hasSize(6);

    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .isEqualTo("Missing function doc comment");
    assertThat(new ArrayList<>(actual).get(0).getReporter()) //
        .isEqualTo("PHP");
  }

  @Test
  public void testThatPHPViolationsCanBeParsedIfNoSource() {
    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/checkstyle/checkstyle-no-source\\.xml$") //
            .inFolder(this.rootFolder) //
            .findAll(CHECKSTYLE) //
            .withReporter("PHP") //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .isEqualTo("Must have at least one statement.");
    assertThat(new ArrayList<>(actual).get(0).getReporter()) //
        .isEqualTo("PHP");
  }

  @Test
  public void testThatDuplicatesAreIgnored() {
    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/checkstyle/duplicates\\.xml$") //
            .inFolder(this.rootFolder) //
            .findAll(CHECKSTYLE) //
            .withReporter("Checkstyle") //
            .violations();

    assertThat(actual) //
        .hasSize(3);

    assertThat(new ArrayList<>(actual).get(0).getMessage()) //
        .isEqualTo("Line is longer than 100 characters (found 312).");
    assertThat(new ArrayList<>(actual).get(0).getReporter()) //
        .isEqualTo("Checkstyle");
  }

  @Test
  public void testThatGolangCILintViolationsCanBeParsed() {
    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/checkstyle/golangci-lint\\.xml$") //
            .inFolder(this.rootFolder) //
            .findAll(CHECKSTYLE) //
            .violations();

    assertThat(actual) //
        .containsExactly( //
            violationBuilder()
                //
                .setParser(CHECKSTYLE)
                //
                .setReporter(CHECKSTYLE.name())
                //
                .setFile("pkg/clients/azure/redis/redis.go")
                //
                .setSource("")
                //
                .setStartLine(41)
                //
                .setEndLine(41)
                //
                .setColumn(1)
                //
                .setMessage(
                    "exported function `NewClient` should have comment or be unexported") //
                .setRule("golint") //
                .setSeverity(ERROR) //
                .build(), //
            violationBuilder() //
                .setParser(CHECKSTYLE) //
                .setReporter(CHECKSTYLE.name()) //
                .setFile("pkg/clients/azure/redis/redis.go") //
                .setSource("") //
                .setStartLine(28) //
                .setEndLine(28) //
                .setColumn(0) //
                .setMessage("File is not `goimports`-ed") //
                .setRule("goimports") //
                .setSeverity(ERROR) //
                .build() //
            );
  }
}
