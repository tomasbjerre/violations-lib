package se.bjurr.violations.lib.reports;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ParserTest {

  @Test
  public void testThatKnownParserNameIsResolved() {
    assertThat(Parser.fromString("checkstyle")) //
        .isEqualTo(Parser.CHECKSTYLE);
    assertThat(Parser.fromString("CHECKSTYLE")) //
        .isEqualTo(Parser.CHECKSTYLE);
  }

  @Test
  public void testThatUnknownParserNameGivesInformativeError() {
    final IllegalArgumentException actual =
        assertThrows(IllegalArgumentException.class, () -> Parser.fromString("ISSUES"));

    assertThat(actual.getMessage()) //
        .contains("Unknown parser \"ISSUES\"") //
        .contains("CHECKSTYLE");
  }
}
