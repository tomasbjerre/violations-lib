package se.bjurr.violations.lib.reports;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.assertj.core.util.Files;
import org.junit.Test;

public class ReporterTest {

  @Test
  public void test() throws IOException {
    File readmeFile = findReadmeFile(new File("."));
    String content = Files.contentOf(readmeFile, Charset.forName("UTF-8"));
    for (Parser shouldBeMentioned : Parser.values()) {
      assertThat(content) //
          .as("All parsers should be mentioned in the README.md") //
          .containsIgnoringCase(shouldBeMentioned.name());
      final int parserIndex =
          content.toLowerCase().indexOf("_" + shouldBeMentioned.name().toLowerCase() + "_") + 1;
      String parsersName =
          content.substring(parserIndex, parserIndex + shouldBeMentioned.name().length());
      String regex =
          "(`[^`]+?`[ ]+?)?(\\[_" + parsersName + "_\\][^\\)]+?\\)|_" + parsersName + "_)";
      Matcher matcher = Pattern.compile(regex).matcher(content);
      if (!matcher.find()) {
        throw new RuntimeException("Did not find " + regex + " in :\n" + content);
      }
      final String original = matcher.group(2);
      final String replaced =
          content.replaceAll(regex, "`" + shouldBeMentioned.name() + "` " + original);
      content = replaced;
    }
    final OpenOption a;
    java.nio.file.Files.write(
        readmeFile.toPath(), content.getBytes(Charset.forName("UTF-8")), StandardOpenOption.CREATE);
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
