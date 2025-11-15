package se.bjurr.violations.lib.reports;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.util.Utils;

public class ReporterTest {

  @Test
  public void test() throws IOException {
    Utils.updateReadmeWithReporters();
  }

  @Test
  public void testAllMapped() {
    for (Parser shouldBeMapped : Parser.values()) {
      boolean found = false;
      for (Reporter rep : Reporter.values()) {
        if (rep.getParser() == shouldBeMapped) {
          found = true;
        }
      }
      assertThat(found) //
          .as("All parsers should be mapped in at least one Reporter") //
          .isTrue();
    }
  }
}
