package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.SEVERITY.WARN;
import static se.bjurr.violations.lib.reports.Parser.RESHARPER;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class ResharperTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/resharper/.*\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(RESHARPER) //
            .violations();

    assertThat(actual) //
        .hasSize(4);

    Violation violation = new ArrayList<>(actual).get(2);
    assertThat(violation.getReporter()) //
        .isEqualTo(RESHARPER.name());

    assertThat(violation.getMessage()) //
        .isEqualTo(
            "Using directive is not required by the code and can be safely removed. Redundancies in Code. Redundant using directive. For more info, visit http://confluence.jetbrains.net/display/ReSharper/Redundant+using+directive");
    assertThat(violation.getRule()) //
        .isEqualTo("RedundantUsingDirective");
    assertThat(violation.getFile()) //
        .isEqualTo("MyLibrary/Class1.cs");
    assertThat(violation.getSeverity()) //
        .isEqualTo(WARN);

    Violation violation2 = new ArrayList<>(actual).get(1);
    assertThat(violation2.getMessage()) //
        .isEqualTo(
            "Join declaration and assignment. Common Practices and Code Improvements. Join local variable declaration and assignment");
    assertThat(violation2.getRule()) //
        .isEqualTo("JoinDeclarationAndInitializer");
    assertThat(violation2.getFile()) //
        .isEqualTo("MyLibrary/Class1.cs");
    assertThat(violation2.getSeverity()) //
        .isEqualTo(INFO);

    Violation violation3 = new ArrayList<>(actual).get(3);
    assertThat(violation3.getMessage()) //
        .isEqualTo(
            "Using directive is not required by the code and can be safely removed. Redundancies in Code. Redundant using directive. For more info, visit http://confluence.jetbrains.net/display/ReSharper/Redundant+using+directive");
    assertThat(violation3.getRule()) //
        .isEqualTo("RedundantUsingDirective");
    assertThat(violation3.getFile()) //
        .isEqualTo("MyLibrary/Properties/AssemblyInfo.cs");
    assertThat(violation3.getSeverity()) //
        .isEqualTo(WARN);

    Violation violation4 = new ArrayList<>(actual).get(0);
    assertThat(violation4.getMessage()) //
        .isEqualTo("Cannot resolve symbol 'GetError'. C# Compiler Errors. CSharpErrors");
    assertThat(violation4.getRule()) //
        .isEqualTo("CSharpErrors");
    assertThat(violation4.getFile()) //
        .isEqualTo("MyLibrary/Class1.cs");
    assertThat(violation4.getSeverity()) //
        .isEqualTo(ERROR);
  }
}
