package se.bjurr.violations.lib.model;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.model.ViolationUtils.relativePath;

import org.junit.Test;

public class ViolationUtilsTest {

  @Test
  public void testRelative() {
    assertThat(relativePath(Violation.NO_FILE, null)).isEqualTo("-");
    assertThat(relativePath("/absolute/file.xml", null)).isEqualTo("absolute/file.xml");
    assertThat(relativePath("relative/file.xml", null)).isEqualTo("relative/file.xml");
    assertThat(relativePath("file.xml", null)).isEqualTo("file.xml");

    assertThat(relativePath("absolute/file.xml", "/absolute")).isEqualTo("absolute/file.xml");
    assertThat(relativePath("absolute/file.xml", "absolute")).isEqualTo("absolute/file.xml");
    assertThat(relativePath("/absolute/file.xml", "/absolute")).isEqualTo("file.xml");
    assertThat(relativePath("/absolute/file.xml", "/absolute/")).isEqualTo("file.xml");
    assertThat(relativePath("/absolute/path/file.xml", "/absolute/")).isEqualTo("path/file.xml");
    assertThat(relativePath("/path/path/file.xml", "/path")).isEqualTo("path/file.xml");
    assertThat(relativePath("path/path/file.xml", "/path")).isEqualTo("path/path/file.xml");
  }
}
