package se.bjurr.violations.lib;

import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.ANDROIDLINT;

import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationAsserter;

/** Created by matthew on 27/04/16. */
public class AndroidLintTest {

  @Test
  public void testThatViolationsCanBeParsed() {

    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/androidlint/lint-results.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(ANDROIDLINT) //
            .violations();

    final Violation v1 =
        violationBuilder()
            .setParser(ANDROIDLINT)
            .setFile("app/src/main/res/layout/fragment_main.xml")
            .setSource(null)
            .setStartLine(10)
            .setEndLine(10)
            .setColumn(9)
            .setRule("ScrollViewSize")
            .setCategory("Correctness")
            .setMessage("ScrollView size validation")
            .setSeverity(WARN) //
            .build();

    final Violation v2 =
        violationBuilder()
            .setParser(ANDROIDLINT)
            .setFile(
                ".gradle/caches/modules-2/files-2.1/com.squareup.okio/okio/1.4.0/5b72bf48563ea8410e650de14aa33ff69a3e8c35/okio-1.4.0.jar")
            .setSource(null)
            .setStartLine(0)
            .setEndLine(0)
            .setColumn(null)
            .setRule("InvalidPackage")
            .setCategory("Correctness")
            .setMessage("Package not included in Android")
            .setSeverity(ERROR) //
            .build();

    ViolationAsserter.assertThat(actual) //
        .contains(v1, 1) //
        .contains(v2, 0);
  }

  @Test
  public void testThatFatalViolationsCanBeParsed() {

    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/androidlint/fatal.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(ANDROIDLINT) //
            .violations();

    final Violation v1 =
        violationBuilder()
            .setParser(ANDROIDLINT)
            .setFile("...")
            .setSource(null)
            .setStartLine(54)
            .setEndLine(54)
            .setColumn(33)
            .setRule("Instantiatable")
            .setCategory("Correctness")
            .setMessage("Registered class")
            .setSeverity(ERROR) //
            .build();

    ViolationAsserter.assertThat(actual) //
        .contains(v1, 0);
  }
}
