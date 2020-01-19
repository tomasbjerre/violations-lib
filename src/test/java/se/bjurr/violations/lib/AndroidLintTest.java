package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.ANDROIDLINT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

/** Created by matthew on 27/04/16. */
public class AndroidLintTest {

  @Test
  public void testThatViolationsCanBeParsed() {

    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsApi() //
            .withPattern(".*/androidlint/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(ANDROIDLINT) //
            .violations();

    assertThat(actual) //
        .containsOnly( //
            violationBuilder() //
                .setParser(ANDROIDLINT) //
                .setFile("app/src/main/res/layout/fragment_main.xml") //
                .setSource(null) //
                .setStartLine(10) //
                .setEndLine(10) //
                .setColumn(9) //
                .setRule("ScrollViewSize") //
                .setCategory("Correctness") //
                .setMessage(
                    "ScrollView size validation\n"
                        + "This LinearLayout should use `android:layout_height=\"wrap_content\"`\n"
                        + "ScrollView children must set their `layout_width` or `layout_height` attributes to `wrap_content` rather than `fill_parent` or `match_parent` in the scrolling dimension") //
                .setSeverity(WARN) //
                .build(), //
            violationBuilder() //
                .setParser(ANDROIDLINT) //
                .setFile(
                    ".gradle/caches/modules-2/files-2.1/com.squareup.okio/okio/1.4.0/5b72bf48563ea8410e650de14aa33ff69a3e8c35/okio-1.4.0.jar") //
                .setSource(null) //
                .setStartLine(0) //
                .setEndLine(0) //
                .setColumn(null) //
                .setRule("InvalidPackage") //
                .setCategory("Correctness") //
                .setMessage(
                    "Package not included in Android\n"
                        + "Invalid package reference in library; not included in Android: `java.nio.file`. Referenced from `okio.Okio`.\n"
                        + "This check scans through libraries looking for calls to APIs that are not included in Android.\n"
                        + "        \n"
                        + "        When you create Android projects, the classpath is set up such that you can only access classes in the API packages that are included in Android. However, if you add other projects to your libs/ folder, there is no guarantee that those .jar files were built with an Android specific classpath, and in particular, they could be accessing unsupported APIs such as java.applet.\n"
                        + "        \n"
                        + "        This check scans through library jars and looks for references to API packages that are not included in Android and flags these. This is only an error if your code calls one of the library classes which wind up referencing the unsupported package.") //
                .setSeverity(ERROR) //
                .build() //
            );
  }
}
