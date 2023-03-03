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
import se.bjurr.violations.lib.parsers.SarifParser;

public class SarifParserTest {

  @Test
  public void testThatViolationsCanBeParsed_smoke() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/sarif/samples/.*(sarif|json)$") //
            .inFolder(rootFolder) //
            .findAll(SARIF) //
            .violations();

    assertThat(actual) //
        .hasSize(53);
  }

  @Test
  public void testThatViolationsCanBeParsed_simple_example() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
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

    final Set<Violation> actual =
        violationsApi() //
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

    final Set<Violation> actual =
        violationsApi() //
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

    final String severities =
        actual.stream()
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

    final Set<Violation> actual =
        violationsApi() //
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

    final Set<Violation> actual =
        violationsApi() //
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
    assertThat(violation0.getSpecifics().get(SarifParser.SARIF_RESULTS_SUPPRESSED)) //
        .isEqualTo("false");

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
    assertThat(violation1.getSpecifics().get(SarifParser.SARIF_RESULTS_SUPPRESSED)) //
        .isEqualTo("false");

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
    assertThat(violation2.getSpecifics().get(SarifParser.SARIF_RESULTS_SUPPRESSED)) //
        .isEqualTo("false");

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
    assertThat(violation3.getSpecifics().get(SarifParser.SARIF_RESULTS_SUPPRESSED)) //
        .isEqualTo("false");

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
    assertThat(violation4.getSpecifics().get(SarifParser.SARIF_RESULTS_SUPPRESSED)) //
        .isEqualTo("true");

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

    final Set<Violation> actual =
        violationsApi() //
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

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/sarif/duplicate-rule-ids.json$") //
            .inFolder(rootFolder) //
            .findAll(SARIF) //
            .violations();

    assertThat(actual) //
        .hasSize(3);
  }

  @Test
  public void testThatViolationsCanBeParsed_with_messages() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/sarif/with-messages.json$") //
            .inFolder(rootFolder) //
            .findAll(SARIF) //
            .violations();

    assertThat(actual) //
        .hasSize(30);
    final List<Violation> arrayList = new ArrayList<>(actual);

    final Violation violation0 = arrayList.get(0);
    assertThat(violation0.getMessage()) //
        .isEqualToIgnoringWhitespace(
            "runs[0].tool.driver.rules[5]: The rule 'CA1822' does not provide a \"friendly name\" in its 'name' property. The friendly name should be a single Pascal-case identifier, for example, 'ProvideRuleFriendlyName', that helps users see at a glance the purpose of the analysis rule.\n"
                + "\n"
                + "For additional help see: Rule metadata should provide information that makes it easy to understand and fix the problem.\n"
                + "\n"
                + "Provide the 'name' property, which contains a \"friendly name\" that helps users see at a glance the purpose of the rule. For uniformity of experience across all tools that produce SARIF, the friendly name should be a single Pascal-case identifier, for example, 'ProvideRuleFriendlyName'.\n"
                + "\n"
                + "Provide the 'helpUri' property, which contains a URI where users can find detailed information about the rule. This information should include a detailed description of the invalid pattern, an explanation of why the pattern is poor practice (particularly in contexts such as security or accessibility where driving considerations might not be readily apparent), guidance for resolving the problem (including describing circumstances in which ignoring the problem altogether might be appropriate), examples of invalid and valid patterns, and special considerations (such as noting when a violation should never be ignored or suppressed, noting when a violation could cause downstream tool noise, and noting when a rule can be configured in some way to refine or alter the analysis).");
    assertThat(violation0.getFile()) //
        .isEqualTo("file:///C:/TEMP/log.sarif");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(261);
    assertThat(violation0.getSeverity()) //
        .isEqualTo(SEVERITY.INFO);
  }

  @Test
  public void testThatViolationsCanBeParsed_dependencyCheck() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/sarif/dependency-check-report.sarif$") //
            .inFolder(rootFolder) //
            .findAll(SARIF) //
            .violations();

    assertThat(actual) //
        .hasSize(1);
    final List<Violation> arrayList = new ArrayList<>(actual);

    final Violation violation0 = arrayList.get(0);
    assertThat(violation0.getMessage()) //
        .isEqualToIgnoringWhitespace(
            "CVE-2021-4277\n"
                + "\n"
                + "        		Medium severity - CVE-2021-4277 Use of Insufficiently Random Values vulnerability in pkg:maven/org.codehaus.plexus/plexus-utils@3.1.1\n"
                + "\n"
                + "        		For additional help see: For more information see [CVE-2021-4277](http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2021-4277).\n"
                + "\n"
                + "\n"
                + "        		If this is a false positive - consider using the HTML report to generate a suppression file. For more information see [How dependency-check works](https://jeremylong.github.io/DependencyCheck/general/internals.html), [How to read the HTML report](https://jeremylong.github.io/DependencyCheck/general/thereport.html), and [Suppressing false positives](https://jeremylong.github.io/DependencyCheck/general/suppression.html).\n"
                + "\n"
                + "        		CVE-2021-4277 - A vulnerability, which was classified as problematic, has been found in fredsmith utils. This issue affects some unknown processing of the file screenshot_sync of the component Filename Handler. The manipulation leads to predictable from observable state. The name of the patch is dbab1b66955eeb3d76b34612b358307f5c4e3944. It is recommended to apply a patch to fix this issue. The identifier VDB-216749 was assigned to this vulnerability.");
    assertThat(violation0.getFile()) //
        .isEqualTo("-");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(0);
    assertThat(violation0.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
  }
}
