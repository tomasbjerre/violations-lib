package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationParserUtils.getChunks;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ViolationsParserTest {

  @Test
  public void testAttributesCanBeExtracted() {
    assertThat(getAttribute("<file name='hej' name2=\"hej2='v'\"/>", "name2")) //
        .isEqualTo("hej2='v'");
    assertThat(getAttribute("<file name='hej'/>", "name")) //
        .isEqualTo("hej");
    assertThat(getAttribute("<file name=\"hej\"/>", "name")) //
        .isEqualTo("hej");
    assertThat(getAttribute("<file name='hej' name2='hej2'/>", "name2")) //
        .isEqualTo("hej2");
  }

  @Test
  public void testChunksCanBeExtracted() {
    List<String> expected = new ArrayList<>();
    expected.add("<file><error name=\"hej\"/></file>");
    expected.add("<file><error name=\"hej\"/></file>");
    assertThat(
            getChunks(
                "<file><error name=\"hej\"/></file><file><error name=\"hej\"/></file>",
                "<file",
                "</file>")) //
        .isEqualTo( //
            expected);
  }
}
