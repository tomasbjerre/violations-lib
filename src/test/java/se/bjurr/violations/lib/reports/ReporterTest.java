package se.bjurr.violations.lib.reports;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

import org.assertj.core.util.Files;
import org.assertj.core.util.Lists;
import org.junit.Test;

public class ReporterTest {

  @Test
  public void test() {
    File readmeFile = findReadmeFile(new File("."));
    String content = Files.contentOf(readmeFile, Charset.forName("UTF-8"));
    for (Parser shouldBeMentioned : Parser.values()) {
      assertThat(content) //
          .as("All parsers should be mentioned in the README.md") //
          .containsIgnoringCase(shouldBeMentioned.name());
    }
  }

  private File findReadmeFile(File file) {
    for (File candidate : file.listFiles()) {
      if (candidate.getName().equals("README.md")) {
        return candidate;
      }
    }

    return findReadmeFile(file.getParentFile());
  }
}
