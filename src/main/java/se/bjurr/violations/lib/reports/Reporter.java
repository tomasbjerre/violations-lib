package se.bjurr.violations.lib.reports;

public enum Reporter {
  ARMGCC("ARM-GCC", Parser.CLANG, "https://developer.arm.com/open-source/gnu-toolchain/gnu-rm", ""),
  ANDROIDLINT(
      "AndroidLint", Parser.ANDROIDLINT, "http://developer.android.com/tools/help/lint.html", ""),
  ANSIBLELINT(
      "AnsibleLint", Parser.FLAKE8, "https://github.com/willthames/ansible-lint", "With `-p`"),
  BANDIT(
      "Bandit",
      Parser.CLANG,
      "https://github.com/PyCQA/bandit",
      "With `bandit -r examples/ -f custom -o bandit.out --msg-template \"{abspath}:{line}: {severity}: {test_id}: {msg}\"`"),
  CLANG("CLang", Parser.CLANG, "https://clang-analyzer.llvm.org/", ""),
  CFN(
      "CloudFormation Linter",
      Parser.JUNIT,
      "https://github.com/aws-cloudformation/cfn-lint",
      "`cfn-lint . -f junit --output-file report-junit.xml`"),
  CPD("CPD", Parser.CPD, "http://pmd.sourceforge.net/pmd-4.3.0/cpd.html", ""),
  CPPCHECK(
      "CPPCheck",
      Parser.CPPCHECK,
      "http://cppcheck.sourceforge.net/",
      "With `cppcheck test.cpp --output-file=cppcheck.xml --xml`"),
  CPPLINT("CPPLint", Parser.CPPLINT, "https://github.com/theandrewdavis/cpplint", ""),
  CSSLINT("CSSLint", Parser.CSSLINT, "https://github.com/CSSLint/csslint", ""),
  CHECKSTYLE("Checkstyle", Parser.CHECKSTYLE, "http://checkstyle.sourceforge.net/", ""),
  CODENARC("CodeNarc", Parser.CODENARC, "http://codenarc.sourceforge.net/", ""),
  CODECLIMATE("CodeClimate", Parser.CODECLIMATE, "https://codeclimate.com/", ""),
  DETEKT(
      "Detekt",
      Parser.CHECKSTYLE,
      "https://github.com/arturbosch/detekt",
      "With `--output-format xml`."),
  ERB(
      "ERB",
      Parser.CLANG,
      "https://www.puppetcookbook.com/posts/erb-template-validation.html",
      "With `erb -P -x -T '-' \"${it}\" | ruby -c 2>&1 >/dev/null | grep '^-' | sed -E 's/^-([a-zA-Z0-9:]+)/${filename}\\1 ERROR:/p' > erbfiles.out`."),
  DOCFX("DocFX", Parser.DOCFX, "http://dotnet.github.io/docfx/", ""),
  DOXYGEN("Doxygen", Parser.CLANG, "https://www.stack.nl/~dimitri/doxygen/", ""),
  ESLINT(
      "ESLint",
      Parser.CHECKSTYLE,
      "https://github.com/sindresorhus/grunt-eslint",
      "With `format: 'checkstyle'`."),
  FINDBUGS("Findbugs", Parser.FINDBUGS, "http://findbugs.sourceforge.net/", ""),
  FLAKE8("Flake8", Parser.FLAKE8, "http://flake8.readthedocs.org/en/latest/", ""),
  FXCOP("FxCop", Parser.FXCOP, "https://en.wikipedia.org/wiki/FxCop", ""),
  GCC("GCC", Parser.CLANG, "https://gcc.gnu.org/", ""),
  GENDARME(
      "Gendarme",
      Parser.GENDARME,
      "http://www.mono-project.com/docs/tools+libraries/tools/gendarme/",
      ""),
  GENERIC(
      "Generic reporter",
      Parser.GENERIC,
      "",
      "Will create one single violation with all the content as message."),
  GOLINT("GoLint", Parser.GOLINT, "https://github.com/golang/lint", ""),
  GOLANGLINT(
      "GolangCI-Lint",
      Parser.CHECKSTYLE,
      "https://github.com/golangci/golangci-lint/",
      "With `--out-format=checkstyle`."),
  GOVET("GoVet", Parser.GOLINT, "https://golang.org/cmd/vet/", "Same format as GoLint."),
  GOOGLEERRORPRONE(
      "GoogleErrorProne", Parser.GOOGLEERRORPRONE, "https://github.com/google/error-prone", ""),
  HADOLINT(
      "HadoLint",
      Parser.CHECKSTYLE,
      "https://github.com/hadolint/hadolint/",
      "With `-f checkstyle`"),
  INFER("Infer", Parser.PMD, "http://fbinfer.com/", "Facebook Infer. With `--pmd-xml`."),
  IAR(
      "IAR",
      Parser.IAR,
      "https://www.iar.com/iar-embedded-workbench/",
      "With `--no_wrap_diagnostics`"),
  JACOCO("JACOCO", Parser.JACOCO, "https://www.jacoco.org/", ""),
  JCREPORT("JCReport", Parser.JCREPORT, "https://github.com/jCoderZ/fawkez/wiki/JcReport", ""),
  JSHINT(
      "JSHint",
      Parser.JSLINT,
      "http://jshint.com/",
      "With `--reporter=jslint` or the CHECKSTYLE parser with `--reporter=checkstyle`"),
  JUNIT("JUnit", Parser.JUNIT, "https://junit.org/junit4/", "It only contains the failures."),
  KTLINT("KTLint", Parser.CHECKSTYLE, "https://github.com/shyiko/ktlint", ""),
  KLOCWORK(
      "Klocwork",
      Parser.KLOCWORK,
      "http://www.klocwork.com/products-services/klocwork/static-code-analysis",
      ""),
  KOTLINGRADLE(
      "KotlinGradle",
      Parser.KOTLINGRADLE,
      "https://github.com/JetBrains/kotlin",
      "Output from Kotlin Gradle Plugin."),
  KOTLINMAVEN(
      "KotlinMaven",
      Parser.KOTLINMAVEN,
      "https://github.com/JetBrains/kotlin",
      "Output from Kotlin Maven Plugin."),
  LINT("Lint", Parser.LINT, "", "A common XML format, used by different linters."),
  MCCABE("Mccabe", Parser.FLAKE8, "https://pypi.python.org/pypi/mccabe", ""),
  MYPY("MyPy", Parser.MYPY, "https://pypi.python.org/pypi/mypy-lang", ""),
  MSBUILDLOG(
      "MSBuildLog",
      Parser.MSBULDLOG,
      "https://docs.microsoft.com/en-us/visualstudio/msbuild/obtaining-build-logs-with-msbuild?view=vs-2019",
      "With `-fileLogger` use `.*msbuild\\\\.log$` as pattern or `-fl -flp:logfile=MyProjectOutput.log;verbosity=diagnostic` for a custom output filename"),
  MSCPP("MSCpp", Parser.MSCPP, "https://visualstudio.microsoft.com/vs/features/cplusplus/", ""),
  NULLAWAY(
      "NullAway",
      Parser.GOOGLEERRORPRONE,
      "https://github.com/uber/NullAway",
      "Same format as Google Error Prone."),
  PCLINT(
      "PCLint",
      Parser.PCLINT,
      "http://www.gimpel.com/html/pcl.htm",
      "PC-Lint using the same output format as the Jenkins warnings plugin, [_details here_](https://wiki.jenkins.io/display/JENKINS/PcLint+options)"),
  PHPCS(
      "PHPCS",
      Parser.CHECKSTYLE,
      "https://github.com/squizlabs/PHP_CodeSniffer",
      "With `phpcs api.php --report=checkstyle`."),
  PHPPMD("PHPPMD", Parser.PMD, "https://phpmd.org/", "With `phpmd api.php xml ruleset.xml`."),
  PMD("PMD", Parser.PMD, "https://pmd.github.io/", ""),
  PEP8("Pep8", Parser.FLAKE8, "https://github.com/PyCQA/pycodestyle", ""),
  PERLCRITIC("PerlCritic", Parser.PERLCRITIC, "https://github.com/Perl-Critic", ""),
  PITEST("PiTest", Parser.PITEST, "http://pitest.org/", ""),
  PROTOLINT("ProtoLint", Parser.PROTOLINT, "https://github.com/yoheimuta/protolint", ""),
  PUPPETLINT(
      "Puppet-Lint",
      Parser.CLANG,
      "http://puppet-lint.com/",
      "With `-log-format %{fullpath}:%{line}:%{column}: %{kind}: %{message}`"),
  PYDOCSTYLE("PyDocStyle", Parser.PYDOCSTYLE, "https://pypi.python.org/pypi/pydocstyle", ""),
  PYFLAKES("PyFlakes", Parser.FLAKE8, "https://pypi.python.org/pypi/pyflakes", ""),
  PYLINT(
      "PyLint",
      Parser.PYLINT,
      "https://www.pylint.org/",
      "With `pylint --output-format=parseable`."),
  RESHARPER("ReSharper", Parser.RESHARPER, "https://www.jetbrains.com/resharper/", ""),
  RUBYCOP(
      "RubyCop",
      Parser.CLANG,
      "http://rubocop.readthedocs.io/en/latest/formatters/",
      "With `rubycop -f clang file.rb`"),
  SARIF(
      "SARIF",
      Parser.SARIFPARSER,
      "http://docs.oasis-open.org/sarif/sarif/v2.0/csprd01/sarif-v2.0-csprd01.html",
      ""),
  SBTSCALAC("SbtScalac", Parser.SBTSCALAC, "http://www.scala-sbt.org/", ""),
  SIMIAN("Simian", Parser.SIMIAN, "http://www.harukizaemon.com/simian/", ""),
  SPOTBUGS("Spotbugs", Parser.FINDBUGS, "https://spotbugs.github.io/", ""),
  STYLECOP("StyleCop", Parser.STYLECOP, "https://stylecop.codeplex.com/", ""),
  SWIFTLINT(
      "SwiftLint",
      Parser.CHECKSTYLE,
      "https://github.com/realm/SwiftLint",
      "With `--reporter checkstyle`."),
  SONAR(
      "Sonar",
      Parser.SONAR,
      "https://www.sonarqube.org/",
      "With `mvn sonar:sonar -Dsonar.analysis.mode=preview -Dsonar.report.export.path=sonar-report.json`."
          + " Removed in 7.7, see [SONAR-11670](https://jira.sonarsource.com/browse/SONAR-11670) but can be retrieved with:"
          + " `curl --silent 'http://sonar-server/api/issues/search?componentKeys=unique-key&resolved=false' | jq -f sonar-report-builder.jq > sonar-report.json`."),
  TSLINT(
      "TSLint",
      Parser.CHECKSTYLE,
      "https://palantir.github.io/tslint/usage/cli/",
      "With `-t checkstyle`"),
  XUNIT("XUnit", Parser.XUNIT, "https://xunit.net/", "It only contains the failures."),
  XMLLINT("XMLLint", Parser.XMLLINT, "http://xmlsoft.org/xmllint.html", ""),
  YAMLLINT(
      "YAMLLint",
      Parser.YAMLLINT,
      "https://yamllint.readthedocs.io/en/stable/index.html",
      "With `-f parsable`"),
  ZPTLINT("ZPTLint", Parser.ZPTLINT, "https://pypi.python.org/pypi/zptlint", ""),
  SCALASTYLE("Scalastyle", Parser.CHECKSTYLE, "http://www.scalastyle.org/", "");

  private final String url;
  private final String name;
  private final Parser parser;
  private final String note;

  Reporter(final String name, final Parser parser, final String url, final String note) {
    this.name = name;
    this.parser = parser;
    this.url = url;
    this.note = note;
  }

  public Parser getParser() {
    return this.parser;
  }

  public String getName() {
    return this.name;
  }

  public String getUrl() {
    return this.url;
  }

  public String getNote() {
    return this.note;
  }
}
