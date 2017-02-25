package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Reporter.CPPCHECK;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class CPPCheckTest {

  private static final String MSG_1 =
      "The scope of the variable 'i' can be reduced. The scope of the variable 'i' can be reduced. Warning: It can be unsafe to fix this message. Be careful. Especially when there are inner loops. Here is an example where cppcheck will write that the scope for 'i' can be reduced:&#xa;void f(int x)&#xa;{&#xa;    int i = 0;&#xa;    if (x) {&#xa;        // it's safe to move 'int i = 0' here&#xa;        for (int n = 0; n &lt; 10; ++n) {&#xa;            // it is possible but not safe to move 'int i = 0' here&#xa;            do_something(&amp;i);&#xa;        }&#xa;    }&#xa;}&#xa;When you see this message it is always safe to reduce the variable scope 1 level.";
  private static final String MSG_2 =
      "The scope of the variable 'i' can be reduced. The scope of the variable 'i' can be reduced. Warning: It can be unsafe to fix this message. Be careful. Especially when there are inner loops. Here is an example where cppcheck will write that the scope for 'i' can be reduced:&#xa;void f(int x)&#xa;{&#xa;    int i = 0;&#xa;    if (x) {&#xa;        // it's safe to move 'int i = 0' here&#xa;        for (int n = 0; n &lt; 10; ++n) {&#xa;            // it is possible but not safe to move 'int i = 0' here&#xa;            do_something(&amp;i);&#xa;        }&#xa;    }&#xa;}&#xa;When you see this message it is always safe to reduce the variable scope 1 level.";

  @Test
  public void testThatViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsReporterApi() //
            .withPattern(".*/cppcheck/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(CPPCHECK) //
            .violations();

    assertThat(actual) //
        .contains( //
            violationBuilder() //
                .setReporter(CPPCHECK) //
                .setFile("api.c") //
                .setStartLine(498) //
                .setEndLine(498) //
                .setRule("variableScope") //
                .setMessage(MSG_1) //
                .setSeverity(INFO) //
                .build()) //
        .contains( //
            violationBuilder() //
                .setReporter(CPPCHECK) //
                .setFile("api_storage.c") //
                .setStartLine(104) //
                .setEndLine(104) //
                .setRule("variableScope") //
                .setMessage(MSG_2) //
                .setSeverity(ERROR) //
                .build()) //
        .hasSize(3);
  }
}
