package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.PCLINT;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class PCLintTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsApi() //
            .withPattern(".*/pclint/.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(PCLINT) //
            .violations();

    assertThat(actual) //
        .hasSize(7);

    assertThat(actual.get(0)) //
        .isEqualTo( //
            violationBuilder() //
                .setParser(PCLINT) //
                .setFile("C:\\UST3\\qse30\\Drivers\\drvADC.c") //
                .setStartLine(84) //
                .setRule("9029") //
                .setMessage(
                    "Mismatched essential type categories for binary operator [MISRA 2012 Rule 10.4, required] (Note <a href=\"/userContent/LintMsgRef.html#9029\">9029</a>)") //
                .setSeverity(INFO) //
                .build() //
            );

    assertThat(actual.get(3)) //
        .isEqualTo( //
            violationBuilder() //
                .setParser(PCLINT) //
                .setFile("C:\\UST3\\qse30\\Drivers\\drvCAN.c") //
                .setStartLine(73) //
                .setRule("534") //
                .setMessage(
                    "Ignoring return value of function 'PIC_CAN_Transmit(can_frame_t *)' (compare with line 68, file C:\\UST3\\qse30\\HAL\\hal_ext.h, module C:\\UST3\\qse30\\Drivers\\drvADC.c) [MISRA 2012 Directive 4.7, required], [MISRA 2012 Rule 17.7, required] (Warning <a href=\"/userContent/LintMsgRef.html#534\">534</a>)") //
                .setSeverity(WARN) //
                .build() //
            );
    assertThat(actual.get(5)) //
        .isEqualTo( //
            violationBuilder() //
                .setParser(PCLINT) //
                .setFile("C:\\UST3\\qse30\\Drivers\\drvCAN.c") //
                .setStartLine(134) //
                .setRule("818") //
                .setMessage(
                    "Pointer parameter 'txFrame' (line 100) could be declared as pointing to const [MISRA 2012 Rule 8.13, advisory] (Info <a href=\"/userContent/LintMsgRef.html#818\">818</a>)") //
                .setSeverity(ERROR) //
                .build() //
            );
    assertThat(actual.get(6)) //
        .isEqualTo( //
            violationBuilder() //
                .setParser(PCLINT) //
                .setFile("C:\\UST3\\qse30\\Modules\\COMM\\J1939\\Broadcast\\dm13.c") //
                .setStartLine(123) //
                .setRule("48") //
                .setMessage(
                    "Bad type [MISRA 2012 Rule 10.1, required] (Error <a href=\"/userContent/LintMsgRef.html#48\">48</a>)") //
                .setSeverity(ERROR) //
                .build() //
            );
  }
}
