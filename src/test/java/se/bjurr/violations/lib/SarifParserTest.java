package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.reports.Parser.SARIF;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class SarifParserTest {

  @Test
  public void testThatViolationsCanBeParsed_smoke() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual = violationsApi() //
        .withPattern(".*/sarif/samples/.*(sarif|json)$") //
        .inFolder(rootFolder) //
        .findAll(SARIF) //
        .violations();

    assertThat(actual) //
        .hasSize(54);
  }

  @Test
  public void testThatViolationsCanBeParsed_simple_example() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual = violationsApi() //
        .withPattern(".*/sarif/.*/simple-example.sarif$") //
        .inFolder(rootFolder) //
        .findAll(SARIF) //
        .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation first = new ArrayList<>(actual).get(0);
    assertThat(first.getMessage()) //
        .isEqualTo("'x' is assigned a value but never used.");
    assertThat(first.getFile()) //
        .isEqualTo("file:///C:/dev/sarif/sarif-tutorials/samples/Introduction/simple-example.js");
    assertThat(first.getSeverity()) //
        .isEqualTo(SEVERITY.ERROR);
    assertThat(first.getRule()) //
        .isEqualTo("no-unused-vars");
    assertThat(first.getStartLine()) //
        .isEqualTo(1);
    assertThat(first.getEndLine()) //
        .isEqualTo(1);
    assertThat(first.getReporter()).isEqualTo("ESLint");
  }

  @Test
  public void testThatViolationsCanBeParsed_result_line_nr() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual = violationsApi() //
        .withPattern(".*/sarif/result_line_nr.json$") //
        .inFolder(rootFolder) //
        .findAll(SARIF) //
        .violations();

    assertThat(actual) //
        .hasSize(443);

    final Violation first = new ArrayList<>(actual).get(0);
    assertThat(first.getMessage()) //
        .isEqualTo(
            "AP_CG_CYCLE: Number of Recursions\n\nFor additional help see: Number of Recursions\n\nThis metric shows the number of recursions, both direct and indirect.");
    assertThat(first.getFile()) //
        .isEqualTo(Violation.NO_FILE);
    assertThat(first.getSeverity()) //
        .isEqualTo(SEVERITY.ERROR);
    assertThat(first.getRule()) //
        .isEqualTo("AP_CG_CYCLE");
    assertThat(first.getStartLine()) //
        .isEqualTo(Violation.NO_LINE);
    assertThat(first.getReporter()).isEqualTo("Polyspace");

    final Violation fourth = new ArrayList<>(actual).get(4);
    assertThat(fourth.getMessage()) //
        .isEqualTo(
            "'unsigned char' doesn't provide information about its size. Define and use typedefs clarifying type and size for numerical types or use one of the exact-width numerical types defined in <stdint.h>.\n\nFor additional help see: [How to resolve polyspace misra-c-2812](https://www.mathworks.com/matlabcentral/answers/479913-how-to-resolve-polyspace-misra-c-2012-d4-14-rule-when-am-passing-pointer-as-parameter-to-function)");
    assertThat(fourth.getFile()) //
        .isEqualTo(
            "file:/c:/var/lib/jenkins/workspace/PSBF_PIControl_DeclPipeline_Access/pi_alg/pi_alg.c");
    assertThat(fourth.getSeverity()) //
        .isEqualTo(SEVERITY.ERROR);
    assertThat(fourth.getRule()) //
        .isEqualTo("MISRA C:2012 D4.6");
    assertThat(fourth.getStartLine()) //
        .isEqualTo(144);
    assertThat(fourth.getEndLine()) //
        .isEqualTo(144);
    assertThat(fourth.getReporter()).isEqualTo("Polyspace");
  }

  @Test
  public void testThatViolationsCanBeParsed_securityscan() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual = violationsApi() //
        .withPattern(".*/sarif/security-scan.json$") //
        .inFolder(rootFolder) //
        .findAll(SARIF) //
        .violations();

    assertThat(actual) //
        .hasSize(51);

    final List<Violation> arrayList = new ArrayList<>(actual);

    final Violation first = arrayList.get(0);
    assertThat(first.getMessage()) //
        .isEqualTo(
            "The cookie is missing 'HttpOnly' flag.\n"
                + "\n"
                + "For additional help see: Cookies without 'HttpOnly' may be stolen in case of JavaScript injection (XSS).");
    assertThat(first.getFile()) //
        .isEqualTo("DummyFile.cs");
    assertThat(first.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);

    final String severities = actual.stream()
        .map(Violation::getSeverity)
        .map(it -> it.name())
        .collect(Collectors.joining(","));
    assertThat(severities) //
        .isEqualTo(
            "WARN,WARN,ERROR,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,INFO,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,INFO,WARN,WARN,WARN,WARN,WARN,WARN,WARN,ERROR,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,WARN,INFO,WARN,ERROR");
  }

  @Test
  public void testThatViolationsCanBeParsed_without_location() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual = violationsApi() //
        .withPattern(".*/sarif/without-location.json$") //
        .inFolder(rootFolder) //
        .findAll(SARIF) //
        .violations();

    assertThat(actual) //
        .hasSize(1);
    final List<Violation> arrayList = new ArrayList<>(actual);

    final Violation first = arrayList.get(0);
    assertThat(first.getMessage()) //
        .isEqualTo("rule.id.2: Rule id 2 title\n\nFor additional help see: Rule id 2 title");
    assertThat(first.getFile()) //
        .isEqualTo(Violation.NO_FILE);
    assertThat(first.getSeverity()) //
        .isEqualTo(SEVERITY.ERROR);
  }

  @Test
  public void testThatViolationsCanBeParsed_with_category() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual = violationsApi() //
        .withPattern(".*/sarif/with-category.json$") //
        .inFolder(rootFolder) //
        .findAll(SARIF) //
        .violations();

    assertThat(actual) //
        .hasSize(6);
    final List<Violation> arrayList = new ArrayList<>(actual);

    final Violation violation0 = arrayList.get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo(
            "CA1014\n\nMark assemblies with CLSCompliant\n\nFor additional help see: The Common Language Specification (CLS) defines naming restrictions, data types, and rules to which assemblies must conform if they will be used across programming languages. Good design dictates that all assemblies explicitly indicate CLS compliance by using CLSCompliantAttribute . If this attribute is not present on an assembly, the assembly is not compliant.");
    assertThat(violation0.getFile()) //
        .isEqualTo(Violation.NO_FILE);
    assertThat(violation0.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation0.getCategory()) //
        .isEqualTo("Design");

    final Violation violation1 = arrayList.get(1);
    assertThat(violation1.getMessage()) //
        .isEqualToIgnoringWhitespace(
            "Exception type System.Exception is not sufficiently specific For additional help see: An exception of type that is not sufficiently specific or reserved by the runtime should never be raised by user code. This makes the original error difficult to detect and debug. If this exception instance might be thrown, use a different exception type.");
    assertThat(violation1.getFile()) //
        .isEqualTo("file:///C:/Projects/SarifCategoryDemo/Class1.cs");
    assertThat(violation1.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation1.getCategory()) //
        .isEqualTo("Usage");

    final Violation violation2 = arrayList.get(2);
    assertThat(violation2.getMessage()) //
        .isEqualToIgnoringWhitespace(
            "The behavior of 'string.Format(string, object)' could vary based on the current user's locale settings. Replace this call in 'Class1.M(string)' with a call to 'string.Format(IFormatProvider, string, params object[])'. For additional help see: A method or constructor calls one or more members that have overloads that accept a System.IFormatProvider parameter, and the method or constructor does not call the overload that takes the IFormatProvider parameter. When a System.Globalization.CultureInfo or IFormatProvider object is not supplied, the default value that is supplied by the overloaded member might not have the effect that you want in all locales. If the result will be based on the input from/output displayed to the user, specify 'CultureInfo.CurrentCulture' as the 'IFormatProvider'. Otherwise, if the result will be stored and accessed by software, such as when it is loaded from disk/database and when it is persisted to disk/database, specify 'CultureInfo.InvariantCulture'.");
    assertThat(violation2.getFile()) //
        .isEqualTo("file:///C:/Projects/SarifCategoryDemo/Class1.cs");
    assertThat(violation2.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation2.getCategory()) //
        .isEqualTo("Globalization");

    final Violation violation3 = arrayList.get(3);
    assertThat(violation3.getMessage()) //
        .isEqualToIgnoringWhitespace(
            "'string.Contains(string)' has a method overload that takes a 'StringComparison' parameter. Replace this call in 'SarifCategoryDemo.Class1.M(string)' with a call to 'string.Contains(string, System.StringComparison)' for clarity of intent. For additional help see: A string comparison operation uses a method overload that does not set a StringComparison parameter. It is recommended to use the overload with StringComparison parameter for clarity of intent. If the result will be displayed to the user, such as when sorting a list of items for display in a list box, specify 'StringComparison.CurrentCulture' or 'StringComparison.CurrentCultureIgnoreCase' as the 'StringComparison' parameter. If comparing case-insensitive identifiers, such as file paths, environment variables, or registry keys and values, specify 'StringComparison.OrdinalIgnoreCase'. Otherwise, if comparing case-sensitive identifiers, specify 'StringComparison.Ordinal'.");
    assertThat(violation3.getFile()) //
        .isEqualTo("file:///C:/Projects/SarifCategoryDemo/Class1.cs");
    assertThat(violation3.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation3.getCategory()) //
        .isEqualTo("Globalization");

    final Violation violation4 = arrayList.get(4);
    assertThat(violation4.getMessage()) //
        .isEqualToIgnoringWhitespace(
            "Use 'string.Contains(char)' instead of 'string.Contains(string)' when searching for a single character For additional help see: 'string.Contains(char)' is available as a better performing overload for single char lookup.");
    assertThat(violation4.getFile()) //
        .isEqualTo("file:///C:/Projects/SarifCategoryDemo/Class1.cs");
    assertThat(violation4.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation4.getCategory()) //
        .isEqualTo("Performance");

    final Violation violation5 = arrayList.get(5);
    assertThat(violation5.getMessage()) //
        .isEqualToIgnoringWhitespace(
            "Member 'M' does not access instance data and can be marked as static For additional help see: Members that do not access instance data or call instance methods can be marked as static. After you mark the methods as static, the compiler will emit nonvirtual call sites to these members. This can give you a measurable performance gain for performance-sensitive code.");
    assertThat(violation5.getFile()) //
        .isEqualTo("file:///C:/Projects/SarifCategoryDemo/Class1.cs");
    assertThat(violation5.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation5.getCategory()) //
        .isEqualTo("Performance");
  }

  @Test
  public void testThatViolationsCanBeParsed_with_tool_configuration_notifications() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual = violationsApi() //
        .withPattern(".*/sarif/with-toolConfigurationNotifications.json$") //
        .inFolder(rootFolder) //
        .findAll(SARIF) //
        .violations();

    assertThat(actual) //
        .hasSize(1);
    final List<Violation> arrayList = new ArrayList<>(actual);

    final Violation violation0 = arrayList.get(0);
    assertThat(violation0.getMessage()) //
        .isEqualTo("Cannot copy from non-file URI: http://example.org/image.png");
    assertThat(violation0.getFile()) //
        .isEqualTo("config.xml");
    assertThat(violation0.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
  }

  @Test
  public void testThatViolationsCanBeParsed_with_duplicate_rule_ids() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual = violationsApi() //
        .withPattern(".*/sarif/duplicate-rule-ids.json$") //
        .inFolder(rootFolder) //
        .findAll(SARIF) //
        .violations();

    assertThat(actual) //
        .hasSize(3);
  }
}
