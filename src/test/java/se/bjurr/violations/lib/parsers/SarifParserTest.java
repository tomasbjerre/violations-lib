package se.bjurr.violations.lib.parsers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import se.bjurr.violations.lib.model.generated.sarif.Message;

public class SarifParserTest {

  @Test
  public void testExtractMessage() {
    SarifParser instance = new SarifParser();
    Message message = null;
    String result = instance.extractMessage(message);
    assertThat(result).isNull();

    message = new Message();
    message.setText("Plain text");
    String expected = "Plain text";
    result = instance.extractMessage(message);
    assertThat(result).isEqualTo(expected);

    message.setMarkdown("__Fancy text__");
    expected = "__Fancy text__";
    result = instance.extractMessage(message);
    assertThat(result).isEqualTo(expected);
  }
}
