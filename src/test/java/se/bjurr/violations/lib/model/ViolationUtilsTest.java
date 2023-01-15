package se.bjurr.violations.lib.model;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.model.ViolationUtils.relativePath;

import java.io.File;
import org.junit.Test;

public class ViolationUtilsTest {

  @Test
  public void testRelative() {
    assertThat(relativePath(Violation.NO_FILE, null)).isEqualTo("-");
    assertThat(relativePath("/absolute/file.xml", null)).isEqualTo("absolute/file.xml");
    assertThat(relativePath("relative/file.xml", null)).isEqualTo("relative/file.xml");
    assertThat(relativePath("file.xml", null)).isEqualTo("file.xml");

    assertThat(relativePath("absolute/file.xml", "/absolute")).isEqualTo("file.xml");
    assertThat(relativePath("absolute/file.xml", "absolute")).isEqualTo("file.xml");
    assertThat(relativePath("/absolute/file.xml", "/absolute")).isEqualTo("file.xml");
    assertThat(relativePath("/absolute/file.xml", "/absolute/")).isEqualTo("file.xml");
    assertThat(relativePath("/absolute/path/file.xml", "/absolute/")).isEqualTo("path/file.xml");
    assertThat(relativePath("/path/path/file.xml", "/path")).isEqualTo("path/file.xml");
    assertThat(relativePath("path/path/file.xml", "/path")).isEqualTo("path/file.xml");
  }

  // @Test
  public void testFindRelativeToRootOfRepo() {
    final String srcFolder =
        this.findSrcFolder(
                    new File(
                        ViolationUtilsTest.class.getResource("/androidlint/fatal.xml").getFile()))
                .getAbsolutePath()
            + "/violations-lib/src";
    assertThat(srcFolder).isEqualTo("/home/bjerre/workspace/violations/violations-lib/src");
    assertThat(relativePath("androidlint/fatal.xml", srcFolder))
        .isEqualTo("test/resources/androidlint/fatal.xml");
    assertThat(
            relativePath("/main/java/se/bjurr/violations/lib/example/OtherClass.java", srcFolder))
        .isEqualTo("main/java/se/bjurr/violations/lib/example/OtherClass.java");
  }

  private File findSrcFolder(final File file) {
    if (new File(file.getAbsolutePath() + "/violations-lib/src").exists()) {
      return file;
    }
    if (file.getParentFile() != null) {
      return this.findSrcFolder(file.getParentFile());
    }
    throw new RuntimeException("src not found");
  }
}
