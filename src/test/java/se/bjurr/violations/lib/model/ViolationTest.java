package se.bjurr.violations.lib.model;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CHECKSTYLE;

import org.junit.Test;
import se.bjurr.violations.lib.model.Violation.ViolationBuilder;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class ViolationTest {

  @Test
  public void testThatFilePathsAreAlwaysFronSlashes() {
    final Violation violation =
        violationBuilder() //
            .setParser(CHECKSTYLE) //
            .setFile("c:\\path\\to\\file.xml") //
            .setMessage("message") //
            .setSeverity(ERROR) //
            .setStartLine(1) //
            .build();
    assertThat(violation.getFile()) //
        .isEqualTo("c:/path/to/file.xml");
  }

  @Test
  public void testThatCopyConstructorWorks() {
    final ViolationBuilder originalBuilder =
        new PodamFactoryImpl().manufacturePojo(ViolationBuilder.class);
    final Violation original = originalBuilder.build();
    final Violation copied = new Violation(original);
    assertThat(copied) //
        .isEqualTo(original);
  }
}
