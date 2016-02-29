package se.bjurr.violations.lib.model;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;

import org.junit.Test;

public class ViolationTest {

 @Test
 public void testThatFilePathsAreAlwaysFronSlashes() {
  assertThat(//
    violationBuilder()//
      .setFile("c:\\path\\to\\file.xml")//
      .setMessage("message")//
      .setSeverity(ERROR)//
      .setStartLine(1)//
      .build()//
      .getFile()//
  )//
  .isEqualTo("c:/path/to/file.xml");
 }

}
