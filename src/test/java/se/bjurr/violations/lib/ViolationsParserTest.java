package se.bjurr.violations.lib;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.parsers.ViolationsParser.getAttribute;
import static se.bjurr.violations.lib.parsers.ViolationsParser.getChunks;

import org.junit.Test;

public class ViolationsParserTest {

 @Test
 public void testAttributesCanBeExtracted() {
  assertThat(getAttribute("<file name='hej' name2=\"hej2='v'\"/>", "name2"))//
    .isEqualTo("hej2='v'");
  assertThat(getAttribute("<file name='hej'/>", "name"))//
    .isEqualTo("hej");
  assertThat(getAttribute("<file name=\"hej\"/>", "name"))//
    .isEqualTo("hej");
  assertThat(getAttribute("<file name='hej' name2='hej2'/>", "name2"))//
    .isEqualTo("hej2");
 }

 @Test
 public void testChunksCanBeExtracted() {
  assertThat(getChunks("<file><error name=\"hej\"/></file><file><error name=\"hej\"/></file>", "<file", "</file>"))//
    .isEqualTo(//
      newArrayList(//
        "<file><error name=\"hej\"/></file>", //
        "<file><error name=\"hej\"/></file>"));
 }
}
