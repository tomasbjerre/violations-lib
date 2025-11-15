package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.*;
import static se.bjurr.violations.lib.reports.Parser.MSBULDLOG;

import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.Violation;

public class MSBuildLogTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/msbuildlog/msbuild\\.log$") //
            .inFolder(rootFolder) //
            .findAll(MSBULDLOG) //
            .violations();

    assertThat(actual) //
        .hasSize(16);

    final Violation violation0 = new ArrayList<>(actual).get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("A closing brace should not be preceded by a blank line.");
    assertThat(violation0.getFile()) //
        .isEqualTo("C:/Users/kaiser/source/repos/ConsoleApp1/ConsoleApp1/Program.cs");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("SA1508");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(16);

    final Violation violation3 = new ArrayList<>(actual).get(11);
    assertThat(violation3.getMessage()) //
        .isEqualTo("Element 'Main' should declare an access modifier");
    assertThat(violation3.getFile()) //
        .isEqualTo("C:/Users/kaiser/source/repos/ConsoleApp1/ConsoleApp1/Program.cs");
    assertThat(violation3.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation3.getRule()) //
        .isEqualTo("SA1400");
    assertThat(violation3.getStartLine()) //
        .isEqualTo(7);
  }

  @Test
  public void testThatDotnetCoreViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/msbuildlog/msbuild\\.sdk-style\\.log$") //
            .inFolder(rootFolder) //
            .findAll(MSBULDLOG) //
            .violations();

    assertThat(actual) //
        .hasSize(2);

    final Violation violation1 = new ArrayList<>(actual).get(0);
    assertThat(violation1.getMessage()) //
        .isEqualTo(
            "Package 'BouncyCastle 1.8.9' was restored using '.NETFramework,Version=v4.6.1, .NETFramework,Version=v4.6.2, .NETFramework,Version=v4.7, .NETFramework,Version=v4.7.1, .NETFramework,Version=v4.7.2, .NETFramework,Version=v4.8, .NETFramework,Version=v4.8.1' instead of the project target framework 'net7.0'. This package may not be fully compatible with your project.");
    assertThat(violation1.getFile()) //
        .isEqualTo("C:/Users/aront/RiderProjects/Warning/Warning/Warning.csproj");
    assertThat(violation1.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation1.getRule()) //
        .isEqualTo("NU1701");

    final Violation violation2 = new ArrayList<>(actual).get(1);
    assertThat(violation2.getMessage()) //
        .isEqualTo(
            "There was a mismatch between the processor architecture of the project being built \"AMD64\" and the processor architecture of the reference \"C:\\Users\\aront\\RiderProjects\\Warning\\Dependency\\bin\\Debug\\net7.0\\Dependency.dll\", \"x86\". This mismatch may cause runtime failures. Please consider changing the targeted processor architecture of your project through the Configuration Manager so as to align the processor architectures between your project and references, or take a dependency on references with a processor architecture that matches the targeted processor architecture of your project.");
    assertThat(violation2.getFile()) //
        .isEqualTo("C:/Users/aront/RiderProjects/Warning/Warning/Warning.csproj");
    assertThat(violation2.getSeverity()) //
        .isEqualTo(WARN);
    assertThat(violation2.getRule()) //
        .isEqualTo("MSB3270");
  }
}
